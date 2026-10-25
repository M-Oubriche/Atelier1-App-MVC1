package ma.fstt.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Commande implements Serializable {
    private Long idCommande;
    private Date dateCommande;
    private String statut;
    private Client client;
    private List<LignedeCommande> lignes = new ArrayList<>();

    public Commande() {
        this.dateCommande = new Date();
        this.statut = "EN_ATTENTE";
    }

    public Commande(Date dateCommande, String statut, Client client) {
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.client = client;
    }

    public Commande(Long idCommande, Date dateCommande, String statut, Client client) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.client = client;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LignedeCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LignedeCommande> lignes) {
        this.lignes = lignes;
    }

    public void ajouterLigne(LignedeCommande ligne) {
        ligne.setCommande(this);
        this.lignes.add(ligne);
    }

    // Calcul du montant total de la commande
    public double getMontantTotal() {
        double total = 0.0;
        if (lignes != null) {
            for (LignedeCommande l : lignes) {
                total += l.getSousTotal();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Commande [id=" + idCommande + ", date=" + dateCommande + ", statut=" + statut + "]";
    }
}
