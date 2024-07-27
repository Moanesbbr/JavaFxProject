package application;

import metier.I_Catalogue;

public class ControleurStock extends ControleurPrincipal{

	public ControleurStock(I_Catalogue catalogueSelectionee) {
		super(catalogueSelectionee);
	}

	public static String afficherStock(){
		return catalogue.toString();
	}
}
