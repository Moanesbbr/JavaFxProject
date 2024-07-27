package metier;

public interface I_Produit {

	public abstract boolean ajouter(int qteAchetee);
	public abstract boolean enlever(int qteVendue);
	public abstract String getNom();
	public abstract I_Catalogue getCatalogue();
	public abstract int getQuantite();
	public abstract void setQuantitee(int qte);
	public abstract double getPrixUnitaireHT();
	public abstract void setPrixUnitaireHT(double prixHt);
	public abstract double getPrixUnitaireTTC();
	public abstract double getPrixStockTTC();
	public abstract String toString();

}