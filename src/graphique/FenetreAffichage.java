package graphique;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FenetreAffichage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3269199765969312063L;
	private JButton btOK;
	
	public FenetreAffichage(String texte) {

		setTitle("Affichage");
		setBounds(500, 500, 450, 250);
		JPanel panHaut = new JPanel();
		JPanel panBas = new JPanel();
		panHaut.setLayout(new BorderLayout());
		panBas.setLayout(new FlowLayout());
		
		JTextArea jtaSortie = new JTextArea("Récaptitulatif du stock : \n",10,5);
		jtaSortie.append(texte);

		jtaSortie.setLineWrap(true);
		jtaSortie.setEditable(false);
		btOK = new JButton("Quitter");
		
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		panHaut.add(jtaSortie);
		panBas.add(btOK);

		contentPane.add(panHaut,"North");
		contentPane.add(panBas, "South");
		btOK.addActionListener(this);

		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}

}
