package ma.fstt.test;

import ma.fstt.dao.ClientDaoImpl;
import ma.fstt.dao.CommandeDaoImpl;
import ma.fstt.dao.ProduitDaoImpl;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.Produit;

import java.util.List;

public class TestDao {
    public static void main(String[] args) {
        System.out.println("=== TEST DE CONNEXION ET DAO ===");
        try {
            ClientDaoImpl clientDao = new ClientDaoImpl();
            ProduitDaoImpl produitDao = new ProduitDaoImpl();
            CommandeDaoImpl commandeDao = new CommandeDaoImpl();

            List<Client> clients = clientDao.findAll();
            System.out.println("Nombre de clients : " + clients.size());

            List<Produit> produits = produitDao.findAll();
            System.out.println("Nombre de produits : " + produits.size());

            List<Commande> commandes = commandeDao.findAll();
            System.out.println("Nombre de commandes : " + commandes.size());

            System.out.println("=== TEST REUSSI ===");
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}