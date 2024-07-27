package dao;

import metier.I_Catalogue;
import metier.I_Produit;

public class DAOFactory_XML extends DAOAbstraiteFactory {

	public DAOFactory_XML() {
	}

	@Override
	public I_DAO<I_Produit> createProduitImplementantDAO() {
		return new AdaptateurProduitDAO_XML();
	}

	@Override
	public I_DAO<I_Catalogue> createCatalogueImplementantDAO() {
		return new CatalogueDAO_XML();
	}

}
