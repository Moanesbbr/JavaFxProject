package dao;

import metier.I_Catalogue;
import metier.I_Produit;

public interface I_DAOFactory {

	public abstract I_DAO<I_Produit> createProduitImplementantDAO();

	public abstract I_DAO<I_Catalogue> createCatalogueImplementantDAO();
	
	public abstract boolean deconnexionBD();
}
