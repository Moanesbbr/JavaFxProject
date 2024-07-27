package application;

import dao.I_DAO;
import graphique.FenetrePrincipale;

import java.util.ArrayList;

import dao.DAOAbstraiteFactory;
import dao.DAOFactory;
import dao.DAOFactory_XML;
import metier.I_Catalogue;
import metier.I_Produit;

@SuppressWarnings("unused")
public class ControleurPrincipal {
	protected static I_Catalogue catalogue;
	protected static I_DAO<I_Produit> produitDao;
	
	public ControleurPrincipal(I_Catalogue catalogueSelectionee){
		catalogue = catalogueSelectionee;
		
//Ligne à changer pour avoir la BDD relationnel
		produitDao = DAOAbstraiteFactory.getInstance("BD").createProduitImplementantDAO();
		
//Ligne à changer pour avoir la BDD XML
		produitDao = DAOAbstraiteFactory.getInstance("XML").createProduitImplementantDAO();
		

		catalogue.addProduits(produitDao.findAll(catalogue.getNomCatalogue()));
		new FenetrePrincipale();
	}
	
	public static String[] getNomsProduits() {
		return ControleurPrincipal.catalogue.getNomProduits();
	}
}