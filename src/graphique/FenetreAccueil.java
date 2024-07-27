package graphique;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import application.ControleurEnsembleCatalogue;


public class FenetreAccueil extends JFrame implements ActionListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5727204080733492910L;
	private JButton btAjouter, btSupprimer, btSelectionner;
	private JTextField txtAjouter;
	private JLabel lbNbCatalogues;
	private JComboBox<String> cmbSupprimer, cmbSelectionner;
	private TextArea taDetailCatalogues;

	public FenetreAccueil() {
		setTitle("Catalogues");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		JPanel panInfosCatalogues = new JPanel();
		JPanel panNbCatalogues = new JPanel();
		JPanel panDetailCatalogues = new JPanel();
		JPanel panGestionCatalogues = new JPanel();
		JPanel panAjouter = new JPanel();
		JPanel panSupprimer = new JPanel();
		JPanel panSelectionner = new JPanel();
		panNbCatalogues.setBackground(Color.white);
		panDetailCatalogues.setBackground(Color.white);
		panAjouter.setBackground(Color.gray);
		panSupprimer.setBackground(Color.lightGray);
		panSelectionner.setBackground(Color.gray);
		
		panNbCatalogues.add(new JLabel("Nous avons actuellement : "));
		lbNbCatalogues = new JLabel();
		panNbCatalogues.add(lbNbCatalogues);
		
		taDetailCatalogues = new TextArea();
		taDetailCatalogues.setEditable(false);
		new JScrollPane(taDetailCatalogues);
		taDetailCatalogues.setPreferredSize(new Dimension(300, 100));
		panDetailCatalogues.add(taDetailCatalogues);

		panAjouter.add(new JLabel("Ajouter un catalogue : "));
		txtAjouter = new JTextField(10);
		panAjouter.add(txtAjouter);
		btAjouter = new JButton("Ajouter");
		panAjouter.add(btAjouter);

		panSupprimer.add(new JLabel("Supprimer un catalogue : "));
		cmbSupprimer = new JComboBox<String>();
		cmbSupprimer.setPreferredSize(new Dimension(100, 20));
		panSupprimer.add(cmbSupprimer);
		btSupprimer = new JButton("Supprimer");
		panSupprimer.add(btSupprimer);

		panSelectionner.add(new JLabel("Selectionner un catalogue : "));
		cmbSelectionner = new JComboBox<String>();
		cmbSelectionner.setPreferredSize(new Dimension(100, 20));
		panSelectionner.add(cmbSelectionner);
		btSelectionner = new JButton("Selectionner");
		panSelectionner.add(btSelectionner);
		
		panGestionCatalogues.setLayout (new BorderLayout());
		panGestionCatalogues.add(panAjouter, "North");
		panGestionCatalogues.add(panSupprimer);
		panGestionCatalogues.add(panSelectionner, "South");
		
		panInfosCatalogues.setLayout(new BorderLayout());
		panInfosCatalogues.add(panNbCatalogues, "North");
		panInfosCatalogues.add(panDetailCatalogues, "South");
				
		contentPane.add(panInfosCatalogues, "North");
		contentPane.add(panGestionCatalogues, "South");
		pack();

		btAjouter.addActionListener(this);
		btSupprimer.addActionListener(this);
		btSelectionner.addActionListener(this);
		
		addWindowListener(this);
		refreshFenetre();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAjouter)
		{
			String texteAjout = txtAjouter.getText();
			if (!texteAjout.equals(""))
			{
				if(ControleurEnsembleCatalogue.getInstance().addCatalogue(texteAjout)){
					refreshFenetre();
					JOptionPane.showMessageDialog(this, "Ajout effectué avec succés.", "Information(s) !", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "Echec de l'ajout.", "Information(s) !", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(this, "Veuillez saisir une valeur.", "Information(s) !", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (e.getSource() == btSupprimer)
		{
			String texteSupprime = (String)cmbSupprimer.getSelectedItem();
			if (texteSupprime != null){
				if(ControleurEnsembleCatalogue.getInstance().deleteCatalogue(texteSupprime)){
					refreshFenetre();
					JOptionPane.showMessageDialog(this, "Catalogue supprimé.", "Information(s) !", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "Erreur suppression.", "Information(s) !", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		}
		if (e.getSource() == btSelectionner)
		{
			String texteSelection = (String)cmbSelectionner.getSelectedItem();
			if (texteSelection != null) 
			{
				ControleurEnsembleCatalogue.getInstance().afficheFenetre(texteSelection);
				this.dispose();
			}
		}
	}

	private void refreshFenetre() {
		modifierListesCatalogues(ControleurEnsembleCatalogue.getInstance().getLesNomsCatalogues());
		modifierDetailCatalogues(ControleurEnsembleCatalogue.getInstance().getLesDetailsCatalogues());
		modifierNbCatalogues(ControleurEnsembleCatalogue.getInstance().getNbCatalogues());
	}

	private void modifierListesCatalogues(String[] nomsCatalogues) {
		cmbSupprimer.removeAllItems();
		cmbSelectionner.removeAllItems();
		if (nomsCatalogues != null)
			for (int i=0 ; i<nomsCatalogues.length; i++)
			{
				cmbSupprimer.addItem(nomsCatalogues[i]);
				cmbSelectionner.addItem(nomsCatalogues[i]);
			}
	}
	
	private void modifierNbCatalogues(int nb)
	{
		lbNbCatalogues.setText(nb + " catalogues");
	}
	
	private void modifierDetailCatalogues(String[] detailCatalogues) {
		taDetailCatalogues.setText("");
		if (detailCatalogues != null) {
			for (int i=0 ; i<detailCatalogues.length; i++)
				taDetailCatalogues.append(detailCatalogues[i]+"\n");
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		ControleurEnsembleCatalogue.deconnexionBD();
		System.out.println("Au revoir");
		System.exit(0);
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}
	
	@Override
	public void windowClosed(WindowEvent arg0) {}
	
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	
	@Override
	public void windowIconified(WindowEvent arg0) {}
	
	@Override
	public void windowOpened(WindowEvent arg0) {}
	
	public static void main(String[] args) {
		new FenetreAccueil();
	}
}