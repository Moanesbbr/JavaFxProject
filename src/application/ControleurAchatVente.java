package application;

import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import metier.I_Catalogue;
import metier.I_Produit;

public class ControleurAchatVente extends ControleurPrincipal {

	public ControleurAchatVente(I_Catalogue catalogueSelectionee) {
		super(catalogueSelectionee);
	}

	private static boolean testEntreeUtilisateur(String txtQte) throws Exception {
		boolean retour;
		if (txtQte.isEmpty()) {
			throw new Exception("Veuillez entrer une valeur !");
		} else if (!Pattern.matches("\\d+", txtQte)) {
			throw new Exception("Veuillez entrer une valeur correcte (Numérique seuleument) !");
		} else {
			if (Integer.parseInt(txtQte) <= 0) {
				throw new Exception("Veuillez entrer une quantité supérieur à zéro !");
			} else if (Integer.parseInt(txtQte) > 0) {
				retour = true;
			} else {
				throw new Exception("Une erreur innatendue s'est produite, veuillez réessayer...");
			}
		}
		return retour;
	}

	public static void achatProduit(JFrame laFrame, String selectedProduit, String txtQte) {
		try {
			if (testEntreeUtilisateur(txtQte) == true) {
				int quantitee = Integer.parseInt(txtQte);
				catalogue.acheterStock(selectedProduit, quantitee);
				I_Produit p = catalogue.getProduitByNom(selectedProduit);
				
				if (produitDao.update(p)) {
					throw new Exception(
						"Votre achat de : " + selectedProduit + " (x" + quantitee + ")a bien été enregistré.");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(laFrame, e.getMessage(), "Information(s) !", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void venteProduit(JFrame laFrame, String selectedProduit, String txtQte) {
		try {
			if (testEntreeUtilisateur(txtQte) == true) {
				int quantitee = Integer.parseInt(txtQte);
				if (!catalogue.vendreStock(selectedProduit, quantitee)) {
					throw new Exception("Pas assez de stock.");
				}
				I_Produit p = catalogue.getProduitByNom(selectedProduit);
				
				if (produitDao.update(p)) {
					throw new Exception(
							"Votre vente de : " + selectedProduit + " (x" + quantitee + ")a bien été enregistré.");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(laFrame, e.getMessage(), "Information(s) !", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
