package ma.fstt.service;

import ma.fstt.entities.Produit;
import java.sql.SQLException;
import java.util.List;

public interface IProduitDao extends IDao<Produit, Long> {
    List<Produit> findDisponibles() throws SQLException;
}
