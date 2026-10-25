package ma.fstt.entities;

import java.io.Serializable;

public class Produit implements Serializable {
    private Long idProduit;
    private String designation;
    private double prixUnitaire;
    private int quantiteStock;
    private String description;

    public Produit() {
    }

    public Produit(String designation, double prixUnitaire, int quantiteStock, String description) {
        this.designation = designation;
        this.prixUnitaire = prixUnitaire;
        this.quantiteStock = quantiteStock;
        this.description = description;
    }

    public Produit(Long idProduit, String designation, double prixUnitaire, int quantiteStock, String description) {
        this.idProduit = idProduit;
        this.designation = designation;
        this.prixUnitaire = prixUnitaire;
        this.quantiteStock = quantiteStock;
        this.description = description;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Produit [id=" + idProduit + ", designation=" + designation + ", prix=" + prixUnitaire + ", stock=" + quantiteStock + "]";
    }
}
