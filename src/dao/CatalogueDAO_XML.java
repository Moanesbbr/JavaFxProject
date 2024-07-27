package dao;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;

public class CatalogueDAO_XML implements I_DAO<I_Catalogue> {
	private String uri = "Catalogues.xml";
	private Document doc;
	private I_DAO<I_Produit> produitDAO;

	public CatalogueDAO_XML() {
		SAXBuilder sdoc = new SAXBuilder();
		try {
			doc = sdoc.build(uri);
			produitDAO = new AdaptateurProduitDAO_XML();
		} catch (Exception e) {
			System.out.println("erreur construction arbre JDOM");
		}
	}

	@Override
	public boolean create(I_Catalogue c) {
		try {
			Element root = doc.getRootElement();
			Element cat = new Element("catalogue");
			cat.setAttribute("nom", c.getNomCatalogue());
			root.addContent(cat);
			return sauvegarde();
		} catch (Exception e) {
			System.out.println("erreur creer catalogue");
			return false;
		}
	}

	@Override
	public boolean update(I_Catalogue c){
		return false;
	}

	@Override
	public boolean delete(I_Catalogue c) {
		try {
			Element root = doc.getRootElement();
			Element cat = chercheCatalogue(c.getNomCatalogue());
			if (cat != null) {
				root.removeContent(cat);
				
				List<I_Produit> lProd = produitDAO.findAll(c.getNomCatalogue());
				for (I_Produit produit : lProd) {
					produitDAO.delete(produit);
				}
				return sauvegarde();
			} else
				return false;
		} catch (Exception e) {
			System.out.println("erreur supprimer catalogue");
			return false;
		}
	}

	@Override
	public List<I_Catalogue> findAll(String nomObj) {

		List<I_Catalogue> l = new ArrayList<I_Catalogue>();
		try {
			Element root = doc.getRootElement();
			List<Element> lProd = root.getChildren("catalogue");

			for (Element prod : lProd) {
				String nomC = prod.getAttributeValue("nom");
				l.add(new Catalogue(nomC));
			}
		} catch (Exception e) {
			System.out.println("erreur lire tous les catalogues");
		}
		return l;
	}

	private boolean sauvegarde() {
		System.out.println("Sauvegarde");
		XMLOutputter out = new XMLOutputter();
		try {
			out.output(doc, new PrintWriter(uri));
			return true;
		} catch (Exception e) {
			System.out.println("erreur sauvegarde dans fichier XML");
			return false;
		}
	}

	private Element chercheCatalogue(String nom) {
		Element root = doc.getRootElement();
		List<Element> lCat = root.getChildren("catalogue");
		int i = 0;
		while (i < lCat.size() && !lCat.get(i).getAttributeValue("nom").equals(nom))
			i++;
		if (i < lCat.size())
			return lCat.get(i);
		else
			return null;
	}

	@Override
	public I_Catalogue findByAttribute(String colonne, Object valeur) {
		Element root = doc.getRootElement();
		List<Element> lCat = root.getChildren("catalogue");
		int i = 0;
		while (i < lCat.size() && !lCat.get(i).getAttributeValue(colonne).equals(valeur))
			i++;
		if (i < lCat.size())
			return new Catalogue((String)valeur);
		else
			return null;
	}

	@Override
	public int getNbTuples(String nomCat) {
		List<I_Produit> lProd = produitDAO.findAll(nomCat);
		return lProd.size();
	}

}
