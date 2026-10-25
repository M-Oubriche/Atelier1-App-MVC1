package ma.fstt.service;

import ma.fstt.entities.Client;
import java.sql.SQLException;

public interface IClientDao extends IDao<Client, Long> {
    Client findByEmail(String email) throws SQLException;
}
