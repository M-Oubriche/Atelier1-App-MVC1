package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LignedeCommande;
import ma.fstt.entities.Produit;
import ma.fstt.service.ICommandeDao;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommandeDaoImpl implements ICommandeDao {

    @Override
    public Commande save(Commande commande) throws SQLException {
        Connection conn = null;
        boolean autoCommitInitial = true;

        String sqlCmd = "INSERT INTO commande (date_commande, statut, id_client) VALUES (?, ?, ?)";
        String sqlLigne = "INSERT INTO ligne_commande (id_commande, id_produit, quantite, prix_vente) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE produit SET quantite_stock = quantite_stock - ? WHERE id_produit = ? AND quantite_stock >= ?";

        try {
            conn = DbConnection.getConnection();
            autoCommitInitial = conn.getAutoCommit();
            conn.setAutoCommit(false);

            // 1. Inserer la commande
            try (PreparedStatement psCmd = conn.prepareStatement(sqlCmd, Statement.RETURN_GENERATED_KEYS)) {
                psCmd.setTimestamp(1, new Timestamp(commande.getDateCommande().getTime()));
                psCmd.setString(2, commande.getStatut());
                psCmd.setLong(3, commande.getClient().getIdClient());
                psCmd.executeUpdate();

                try (ResultSet rs = psCmd.getGeneratedKeys()) {
                    if (rs.next()) {
                        commande.setIdCommande(rs.getLong(1));
                    }
                }
            }

            // 2. Inserer les lignes et mettre a jour le stock
            if (commande.getLignes() != null && !commande.getLignes().isEmpty()) {
                try (PreparedStatement psLigne = conn.prepareStatement(sqlLigne, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement psStock = conn.prepareStatement(sqlStock)) {

                    for (LignedeCommande ligne : commande.getLignes()) {
                        psLigne.setLong(1, commande.getIdCommande());
                        psLigne.setLong(2, ligne.getProduit().getIdProduit());
                        psLigne.setInt(3, ligne.getQuantite());
                        psLigne.setDouble(4, ligne.getPrixVente());
                        psLigne.executeUpdate();

                        try (ResultSet rsL = psLigne.getGeneratedKeys()) {
                            if (rsL.next()) {
                                ligne.setIdLigne(rsL.getLong(1));
                            }
                        }

                        // Mise a jour securisee du stock (empeche un stock negatif)
                        psStock.setInt(1, ligne.getQuantite());
                        psStock.setLong(2, ligne.getProduit().getIdProduit());
                        psStock.setInt(3, ligne.getQuantite());
                        int updated = psStock.executeUpdate();
                        if (updated == 0) {
                            throw new SQLException("Stock insuffisant pour le produit : " + ligne.getProduit().getDesignation());
                        }
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(autoCommitInitial);
                conn.close();
            }
        }

        return commande;
    }

    @Override
    public Commande update(Commande commande) throws SQLException {
        String sql = "UPDATE commande SET statut=? WHERE id_commande=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, commande.getStatut());
            ps.setLong(2, commande.getIdCommande());
            ps.executeUpdate();
        }
        return commande;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM commande WHERE id_commande=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Commande findById(Long id) throws SQLException {
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.email, cl.telephone, cl.adresse " +
                     "FROM commande c " +
                     "JOIN client cl ON c.id_client = cl.id_client " +
                     "WHERE c.id_commande=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client(
                        rs.getLong("id_client"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                    );
                    Commande cmd = new Commande(
                        rs.getLong("id_commande"),
                        rs.getTimestamp("date_commande"),
                        rs.getString("statut"),
                        client
                    );
                    cmd.setLignes(findLignesByCommandeId(cmd.getIdCommande()));
                    return cmd;
                }
            }
        }
        return null;
    }

    @Override
    public List<Commande> findAll() throws SQLException {
        List<Commande> liste = new ArrayList<>();
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.email, cl.telephone, cl.adresse " +
                     "FROM commande c " +
                     "JOIN client cl ON c.id_client = cl.id_client " +
                     "ORDER BY c.date_commande DESC";
        try (Connection conn = DbConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Client client = new Client(
                    rs.getLong("id_client"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("adresse")
                );
                Commande cmd = new Commande(
                    rs.getLong("id_commande"),
                    rs.getTimestamp("date_commande"),
                    rs.getString("statut"),
                    client
                );
                cmd.setLignes(findLignesByCommandeId(cmd.getIdCommande()));
                liste.add(cmd);
            }
        }
        return liste;
    }

    @Override
    public List<LignedeCommande> findLignesByCommandeId(Long idCommande) throws SQLException {
        List<LignedeCommande> lignes = new ArrayList<>();
        String sql = "SELECT lc.*, p.designation, p.prix_unitaire, p.quantite_stock, p.description " +
                     "FROM ligne_commande lc " +
                     "JOIN produit p ON lc.id_produit = p.id_produit " +
                     "WHERE lc.id_commande = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idCommande);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produit p = new Produit(
                        rs.getLong("id_produit"),
                        rs.getString("designation"),
                        rs.getDouble("prix_unitaire"),
                        rs.getInt("quantite_stock"),
                        rs.getString("description")
                    );
                    LignedeCommande ligne = new LignedeCommande(
                        rs.getLong("id_ligne"),
                        rs.getInt("quantite"),
                        rs.getDouble("prix_vente"),
                        null,
                        p
                    );
                    lignes.add(ligne);
                }
            }
        }
        return lignes;
    }

    @Override
    public boolean updateStatut(Long idCommande, String statut) throws SQLException {
        String sql = "UPDATE commande SET statut=? WHERE id_commande=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statut);
            ps.setLong(2, idCommande);
            return ps.executeUpdate() > 0;
        }
    }
}