package ma.fstt.dao;

import ma.fstt.entities.Produit;
import ma.fstt.service.IProduitDao;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProduitDaoImpl implements IProduitDao {

    @Override
    public Produit save(Produit produit) throws SQLException {
        String sql = "INSERT INTO produit (designation, prix_unitaire, quantite_stock, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, produit.getDesignation());
            ps.setDouble(2, produit.getPrixUnitaire());
            ps.setInt(3, produit.getQuantiteStock());
            ps.setString(4, produit.getDescription());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    produit.setIdProduit(rs.getLong(1));
                }
            }
        }
        return produit;
    }

    @Override
    public Produit update(Produit produit) throws SQLException {
        String sql = "UPDATE produit SET designation=?, prix_unitaire=?, quantite_stock=?, description=? WHERE id_produit=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, produit.getDesignation());
            ps.setDouble(2, produit.getPrixUnitaire());
            ps.setInt(3, produit.getQuantiteStock());
            ps.setString(4, produit.getDescription());
            ps.setLong(5, produit.getIdProduit());
            ps.executeUpdate();
        }
        return produit;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM produit WHERE id_produit=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Produit findById(Long id) throws SQLException {
        String sql = "SELECT * FROM produit WHERE id_produit=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapProduit(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Produit> findAll() throws SQLException {
        List<Produit> liste = new ArrayList<>();
        String sql = "SELECT * FROM produit ORDER BY designation ASC";
        try (Connection conn = DbConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(mapProduit(rs));
            }
        }
        return liste;
    }

    @Override
    public List<Produit> findDisponibles() throws SQLException {
        List<Produit> liste = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE quantite_stock > 0 ORDER BY designation ASC";
        try (Connection conn = DbConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(mapProduit(rs));
            }
        }
        return liste;
    }

    private Produit mapProduit(ResultSet rs) throws SQLException {
        return new Produit(
            rs.getLong("id_produit"),
            rs.getString("designation"),
            rs.getDouble("prix_unitaire"),
            rs.getInt("quantite_stock"),
            rs.getString("description")
        );
    }
}
