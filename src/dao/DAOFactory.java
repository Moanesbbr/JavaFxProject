package dao;

import java.sql.Connection;

import bd.ConnexionBD;
import metier.I_Catalogue;
import metier.I_Produit;

public class DAOFactory extends DAOAbstraiteFactory {
	private static final Connection connect = ConnexionBD.getInstance();

	public DAOFactory() {
	}

	/**
	 * @return un objet Produit interagissant avec la BD
	 */
	public I_DAO<I_Produit> createProduitImplementantDAO() {
		return new ProduitDAO(connect);
	}

	/**
	 * @return un objet Produit interagissant avec la BD
	 */
	public I_DAO<I_Catalogue> createCatalogueImplementantDAO() {
		return new CatalogueDAO(connect);
	}

	public boolean deconnexionBD() {
		return ConnexionBD.deconnexion();
	}

}
