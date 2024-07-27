package graphique;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import application.ControleurProduit;

public class FenetreSuppressionProduit extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3712866553831523174L;
	private JButton btSupprimer;
	private JComboBox<String> combo;
	
	public FenetreSuppressionProduit(String lesProduits[]) {
		
		setTitle("Suppression produit");
		setBounds(500, 500, 200, 105);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btSupprimer = new JButton("Supprimer");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(btSupprimer);

		btSupprimer.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		this.dispose();
		if ( ControleurProduit.supprimerProduit((String) combo.getSelectedItem()) ) {
			JOptionPane.showMessageDialog(this, "Le produit " + combo.getSelectedItem() + " a été bien supprimé.", "Suppression de produit", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Produit supprimé");
		} else {
			JOptionPane.showMessageDialog(this, "Erreur sur la suppression d'un produit", "Suppression de produit", JOptionPane.WARNING_MESSAGE);
			System.out.println("Produit non-supprimé");
		}
	}

}
