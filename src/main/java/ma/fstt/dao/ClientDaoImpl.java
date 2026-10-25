package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.service.IClientDao;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientDaoImpl implements IClientDao {

    @Override
    public Client save(Client client) throws SQLException {
        String sql = "INSERT INTO client (nom, prenom, email, telephone, adresse) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getTelephone());
            ps.setString(5, client.getAdresse());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    client.setIdClient(rs.getLong(1));
                }
            }
        }
        return client;
    }

    @Override
    public Client update(Client client) throws SQLException {
        String sql = "UPDATE client SET nom=?, prenom=?, email=?, telephone=?, adresse=? WHERE id_client=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getTelephone());
            ps.setString(5, client.getAdresse());
            ps.setLong(6, client.getIdClient());
            ps.executeUpdate();
        }
        return client;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM client WHERE id_client=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Client findById(Long id) throws SQLException {
        String sql = "SELECT * FROM client WHERE id_client=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapClient(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Client> findAll() throws SQLException {
        List<Client> liste = new ArrayList<>();
        String sql = "SELECT * FROM client ORDER BY nom ASC";
        try (Connection conn = DbConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(mapClient(rs));
            }
        }
        return liste;
    }

    @Override
    public Client findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM client WHERE email=?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapClient(rs);
                }
            }
        }
        return null;
    }

    private Client mapClient(ResultSet rs) throws SQLException {
        return new Client(
            rs.getLong("id_client"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("email"),
            rs.getString("telephone"),
            rs.getString("adresse")
        );
    }
}
