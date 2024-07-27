package dao;

import metier.I_Catalogue;
import metier.I_Produit;

public abstract class DAOAbstraiteFactory implements I_DAOFactory {

	public static DAOAbstraiteFactory instance = null;

	protected DAOAbstraiteFactory() {}

	public synchronized static DAOAbstraiteFactory getInstance(String technologie) {
		if (instance == null) {
			if (technologie == "XML") {
				instance = new DAOFactory_XML();
			} else {
				instance = new DAOFactory();
			}

		}
		return instance;
	}

	@Override
	public abstract I_DAO<I_Produit> createProduitImplementantDAO();

	@Override
	public abstract I_DAO<I_Catalogue> createCatalogueImplementantDAO();
	
	@Override
	public boolean deconnexionBD() {
		return false;
	}

}
