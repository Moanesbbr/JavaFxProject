package graphique;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import application.ControleurProduit;

public class FenetreNouveauProduit extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7003676771126229256L;
	private JTextField txtPrixHT;
	private JTextField txtNom;
	private JTextField txtQte;
//	private JComboBox<String> combo;
	private JButton btValider;

//	public FenetreNouveauProduit(String[] lesCategories) {
	public FenetreNouveauProduit() {	

		setTitle("Creation Produit");
		setBounds(500, 500, 200, 250);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());

		JLabel labNom = new JLabel("Nom produit");
		JLabel labPrixHT = new JLabel("Prix Hors Taxe");
		JLabel labQte = new JLabel("Quantité en stock");
//		JLabel labCategorie = new JLabel("Categorie");
		contentPane.add(labNom);
		txtNom = new JTextField(15);
		contentPane.add(txtNom);
		contentPane.add(labPrixHT);
		txtPrixHT = new JTextField(15);
		contentPane.add(txtPrixHT);
		contentPane.add(labQte);
		txtQte = new JTextField(15);
		contentPane.add(txtQte);

//		combo = new JComboBox<String>(lesCategories);
//		combo.setPreferredSize(new Dimension(100, 20));
//		contentPane.add(labCategorie);
//		contentPane.add(combo);

		
		btValider = new JButton("Valider");
		contentPane.add(btValider);

		btValider.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		this.dispose();
		if (e.getSource() == this.btValider) {
			String nomProduit = txtNom.getText();
			String prixProduit = txtPrixHT.getText();
			String qteProduit = txtQte.getText();
			
			if (ControleurProduit.ajouterProduit(nomProduit, prixProduit, qteProduit, this)) {
				System.out.println("Noveau produit ajouté");
			} else {
				System.out.println("Produit non-ajouté");
			}
		}
	}

}