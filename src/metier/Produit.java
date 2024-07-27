package metier;

import java.text.DecimalFormat;

public class Produit implements I_Produit {
	private int quantiteStock = 0;
	private String nom = "Inconnu";
	private double prixUnitaireHT = 0;
	private static final float tauxTVA = (float) 0.2;
	private I_Catalogue catalogue;

	public Produit(String nom, double prixUnitaireHT, int quantiteStock, I_Catalogue catalogue) {
		this.nom = nom;
		this.prixUnitaireHT = prixUnitaireHT;
		this.quantiteStock = quantiteStock;
		this.catalogue = catalogue;
	}

	@Override
	public boolean ajouter(int qteAchetee) {
		quantiteStock = quantiteStock + qteAchetee;
		return true;
	}

	@Override
	public boolean enlever(int qteVendue) {
		boolean retour = false;
		if (quantiteStock >= qteVendue) {
			quantiteStock = quantiteStock - qteVendue;
			retour = true;
		}
		return retour;
	}

	@Override
	public String getNom() {
		return nom.replace("	", " ").trim();
	}

	@Override
	public int getQuantite() {
		return quantiteStock;
	}

	@Override
	public double getPrixUnitaireHT() {
		return prixUnitaireHT;
	}

	@Override
	public double getPrixUnitaireTTC() {
		return prixUnitaireHT * (1 + tauxTVA);
	}

	@Override
	public double getPrixStockTTC() {
		return getPrixUnitaireTTC() * quantiteStock;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#0.00");
		return this.getNom() + " - prix HT : " + df.format(this.getPrixUnitaireHT()) + " €" + " - prix TTC : "
				+ df.format(this.getPrixUnitaireTTC()) + " €" + " - quantité en stock : " + this.getQuantite() + "\n";

	}

	@Override
	public I_Catalogue getCatalogue() {
		return this.catalogue;
	}

	@Override
	public void setQuantitee(int qte) {
		this.quantiteStock = qte;
	}

	@Override
	public void setPrixUnitaireHT(double prixHt) {
		if (prixHt > 0)
			this.prixUnitaireHT = prixHt;
	}

}
