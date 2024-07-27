package graphique;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import application.ControleurAchatVente;

public class FenetreVente extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509810633838914324L;
	private JButton btVente;
	private JTextField txtQuantite;
	private JComboBox<String> combo;

	public FenetreVente(String[] strings) {
		setTitle("Vente");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btVente = new JButton("Vente");
		txtQuantite = new JTextField(5);
		txtQuantite.setText("0");

		combo = new JComboBox<String>();
		for (int i = 0; i < strings.length; i++) {
			combo.addItem(strings[i]);
			
		}
		
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(new JLabel("Quantité vendue"));
		contentPane.add(txtQuantite);
		contentPane.add(btVente);

		btVente.addActionListener(this);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btVente){
			ControleurAchatVente.venteProduit(this, (String) combo.getSelectedItem(), txtQuantite.getText());
		}
	}

}
