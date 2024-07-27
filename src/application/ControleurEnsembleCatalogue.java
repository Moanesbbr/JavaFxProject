package application;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import dao.DAOAbstraiteFactory;
import dao.I_DAO;
import metier.Catalogue;
import metier.I_Catalogue;

public class ControleurEnsembleCatalogue {
	private static List<I_Catalogue> lesCatalogues;
	private static ControleurEnsembleCatalogue instance = null;
	private static I_DAO<I_Catalogue> catalogueDAO;

	private ControleurEnsembleCatalogue() {
		catalogueDAO = DAOAbstraiteFactory.getInstance("XML").createCatalogueImplementantDAO();
		lesCatalogues = catalogueDAO.findAll(null);
	}

	public static synchronized ControleurEnsembleCatalogue getInstance() {
		if (instance == null) {
			instance = new ControleurEnsembleCatalogue();
		}
		return instance;
	}

	public String[] getLesNomsCatalogues() {
		String[] nomsCatalogues = new String[0];
		if (lesCatalogues != null) {
			nomsCatalogues = new String[lesCatalogues.size()];
			int index = 0;
			for (Iterator<I_Catalogue> i = lesCatalogues.iterator(); i.hasNext();) {
				I_Catalogue unCata = i.next();
				nomsCatalogues[index] = unCata.getNomCatalogue();
				index++;
			}
			Arrays.sort(nomsCatalogues);
		}
		return nomsCatalogues;
	}

	public String[] getLesDetailsCatalogues() {
		String[] detailsCatalogues = new String[0];
		if (lesCatalogues != null) {
			detailsCatalogues = new String[lesCatalogues.size()];
			int index = 0;
			for (Iterator<I_Catalogue> i = lesCatalogues.iterator(); i.hasNext();) {
				I_Catalogue unCata = i.next();
				String nomCatalogue = unCata.getNomCatalogue();
				int nbProduitDansCatalogue = catalogueDAO.getNbTuples(nomCatalogue);
				detailsCatalogues[index] = nomCatalogue + " : " + nbProduitDansCatalogue + " produits.";
				index++;
			}
			Arrays.sort(detailsCatalogues);
		}
		return detailsCatalogues;
	}

	public int getNbCatalogues() {
		return lesCatalogues.size();
	}

	public List<I_Catalogue> getLesCatalogues() {
		return lesCatalogues;
	}

	private I_Catalogue getUnCatalogue(String nomCat) {
		for (Iterator<I_Catalogue> i = lesCatalogues.iterator(); i.hasNext();) {
			I_Catalogue i_Catalogue = (I_Catalogue) i.next();
			if (i_Catalogue.getNomCatalogue() == nomCat) {
				return i_Catalogue;
			}
		}
		return null;
	}

	public boolean addCatalogue(String nomCat) {
		if (!estDansEnsembleCatalogue(nomCat)) {
			I_Catalogue c = new Catalogue(nomCat);
			return catalogueDAO.create(c) && lesCatalogues.add(c);
		}
		return false;
	}

	private boolean estDansEnsembleCatalogue(String nomCat) {
		for (Iterator<I_Catalogue> i = lesCatalogues.iterator(); i.hasNext();) {
			I_Catalogue i_Catalogue = (I_Catalogue) i.next();
			if (i_Catalogue.getNomCatalogue() == nomCat) {
				return true;
			}
		}
		return false;
	}

	public boolean deleteCatalogue(String nomCat) {
		I_Catalogue cat = getUnCatalogue(nomCat);
		if (cat != null) {
			return catalogueDAO.delete(cat) && lesCatalogues.remove(cat);
		}
		return false;
	}

	public void afficheFenetre(String texteSelection) {
		I_Catalogue c = getUnCatalogue(texteSelection);
		if (c != null) {
			new ControleurPrincipal(c);
		}
	}

	public static void deconnexionBD() {
		DAOAbstraiteFactory.getInstance(null).deconnexionBD();
	}
}
