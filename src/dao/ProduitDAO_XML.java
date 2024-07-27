package dao;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

public class ProduitDAO_XML {
	private String uri = "Produits.xml";
	private Document doc;

	public ProduitDAO_XML() {
		SAXBuilder sdoc = new SAXBuilder();
		try {
			doc = sdoc.build(uri);
		} catch (Exception e) {
			System.out.println("erreur construction arbre JDOM");
		}
	}

	public boolean creer(I_Produit p) {
		try {
			Element root = doc.getRootElement();
			Element prod = new Element("produit");
			prod.setAttribute("nom", p.getNom());
			Element prix = new Element("prixHT");
			prod.addContent(prix.setText(String.valueOf(p.getPrixUnitaireHT())));
			Element qte = new Element("quantite");
			prod.addContent(qte.setText(String.valueOf(p.getQuantite())));
			Element catalogue = new Element("catalogue");
			prod.addContent(catalogue.setText(p.getCatalogue().getNomCatalogue()));
			root.addContent(prod);
			return sauvegarde();
		} catch (Exception e) {
			System.out.println("erreur creer produit");
			return false;
		}
	}

	public boolean maj(I_Produit p) {
		try {
			Element prod = chercheProduit(p.getNom(), p.getCatalogue().getNomCatalogue());
			if (prod != null) {
				prod.getChild("quantite").setText(String.valueOf(p.getQuantite()));
				return sauvegarde();
			}
			return false;
		} catch (Exception e) {
			System.out.println("erreur maj produit");
			return false;
		}
	}

	public boolean supprimer(I_Produit p) {
		try {
			Element root = doc.getRootElement();
			Element prod = chercheProduit(p.getNom(), p.getCatalogue().getNomCatalogue());
			if (prod != null) {
				root.removeContent(prod);
				return sauvegarde();
			} else
				return false;
		} catch (Exception e) {
			System.out.println("erreur supprimer produit");
			return false;
		}
	}

	public I_Produit lire(String nom, String catalogue) {
		Element e = chercheProduit(nom, catalogue);
		if (e != null) {
			String nomProduit = e.getAttributeValue("nom");
			double prix = Double.parseDouble(e.getChildText("prixHT"));
			int qteProduit = Integer.parseInt(e.getChildText("quantite"));
			I_Catalogue cat = new Catalogue(e.getChildText("catalogue"));
			return new Produit(nomProduit, prix, qteProduit, cat);
		} else {
			return null;
		}

	}

	public List<I_Produit> lireTous(String nomCatalogue) {

		List<I_Produit> l = new ArrayList<I_Produit>();
		I_Catalogue catalogue = new Catalogue(nomCatalogue);
		try {
			Element root = doc.getRootElement();
			List<Element> lProd = root.getChildren("produit");
			
			
			
			for (Element prod : lProd) {
				if (prod.getChild("catalogue").getText().equals(nomCatalogue)){
					String nomP = prod.getAttributeValue("nom");
					Double prix = Double.parseDouble(prod.getChild("prixHT").getText());
					int qte = Integer.parseInt(prod.getChild("quantite").getText());
					l.add(new Produit(nomP, prix, qte, catalogue));
				}
			}
		} catch (Exception e) {
			System.out.println("erreur lireTous tous les produits");
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

	private Element chercheProduit(String nom, String catalogue) {
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> lProd = root.getChildren("produit");
		int i = 0;
		
		while (i < lProd.size() && !lProd.get(i).getAttributeValue("nom").equals(nom) && 
				lProd.get(i).getChild("catalogue").getText().equals(catalogue))
			i++;
		if (i < lProd.size())
			return lProd.get(i);
		else
			return null;
	}
	
	public int getNbTuples(String nom){
		Element root = doc.getRootElement();
		return root.getChildren().size();
	}
}
