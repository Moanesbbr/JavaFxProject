package dao;

import java.util.List;

import metier.I_Produit;

public class AdaptateurProduitDAO_XML implements I_DAO<I_Produit> {
	
	private ProduitDAO_XML prod_XML;

	public AdaptateurProduitDAO_XML() {
		super();
		prod_XML = new ProduitDAO_XML();
	}

	@Override
	public boolean create(I_Produit obj) {
		return prod_XML.creer((I_Produit) obj);
	}

	@Override
	public boolean update(I_Produit p) throws QuantiteeStock_Exception {
		if((p.getQuantite() + p.getQuantite())<0){
			throw (new QuantiteeStock_Exception("Pas assez de stock."));
		}
		return prod_XML.maj(p);
	}

	@Override
	public boolean delete(I_Produit p) {
		return prod_XML.supprimer(p);
	}

	@Override
	public List<I_Produit> findAll(String nomCatalogue) {
		return prod_XML.lireTous(nomCatalogue);
	}

	@Override
	public I_Produit findByAttribute(String colonne, Object valeur) {
		return null;
	}

	@Override
	public int getNbTuples(String nom) {
		return prod_XML.getNbTuples(nom);
	}

}
