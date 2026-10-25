package ma.fstt.service;

import ma.fstt.entities.Commande;
import ma.fstt.entities.LignedeCommande;
import java.sql.SQLException;
import java.util.List;

public interface ICommandeDao extends IDao<Commande, Long> {
    List<LignedeCommande> findLignesByCommandeId(Long idCommande) throws SQLException;
    boolean updateStatut(Long idCommande, String statut) throws SQLException;
}
