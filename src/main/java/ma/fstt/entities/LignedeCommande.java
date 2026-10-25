package ma.fstt.entities;

import java.io.Serializable;

public class LignedeCommande implements Serializable {
    private Long idLigne;
    private int quantite;
    private double prixVente;
    private Commande commande;
    private Produit produit;

    public LignedeCommande() {
    }

    public LignedeCommande(int quantite, double prixVente, Commande commande, Produit produit) {
        this.quantite = quantite;
        this.prixVente = prixVente;
        this.commande = commande;
        this.produit = produit;
    }

    public LignedeCommande(Long idLigne, int quantite, double prixVente, Commande commande, Produit produit) {
        this.idLigne = idLigne;
        this.quantite = quantite;
        this.prixVente = prixVente;
        this.commande = commande;
        this.produit = produit;
    }

    public Long getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(Long idLigne) {
        this.idLigne = idLigne;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    // Calcul du sous-total pour la ligne
    public double getSousTotal() {
        return quantite * prixVente;
    }

    @Override
    public String toString() {
        return "LignedeCommande [id=" + idLigne + ", qte=" + quantite + ", prixVente=" + prixVente + "]";
    }
}
