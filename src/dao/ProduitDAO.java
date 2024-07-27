package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

public class ProduitDAO implements I_DAO<I_Produit> {

	private Connection connect;

	public ProduitDAO() {
		super();
		this.connect = null;
	}

	public ProduitDAO(Connection connect) {
		super();
		this.connect = connect;
	}

	@Override
	public boolean create(I_Produit obj) {
		String nomProduit = obj.getNom();
		int qteStock = obj.getQuantite();
		double prixHT = obj.getPrixUnitaireHT();
		String nomCatalogueProduit = obj.getCatalogue().getNomCatalogue();

		try {
			CallableStatement cst = connect.prepareCall("{call nouveauProduit(?,?,?,?)}");
			cst.setString(1, nomProduit);
			cst.setInt(2, qteStock);
			cst.setDouble(3, prixHT);
			cst.setString(4, nomCatalogueProduit);
			cst.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean update(I_Produit produitAMettreJour) throws QuantiteeStock_Exception {
		String nomProduit = produitAMettreJour.getNom();
		String nomCatalogueProduit = produitAMettreJour.getCatalogue().getNomCatalogue();
		int quantiteNouvelle = produitAMettreJour.getQuantite();
		I_Produit p = findByAttribute("nomProduit", nomProduit);
		
		if ((p.getQuantite() + quantiteNouvelle) < 0) {
			throw (new QuantiteeStock_Exception("Pas assez de stock."));
		}
		
		try {
			PreparedStatement pst = connect.prepareStatement(
					"UPDATE PRODUITS p SET quantiteStock = ? WHERE nomProduit = ? AND p.idCatalogue = (SELECT c.idCatalogue FROM Catalogue c WHERE nomCatalogue = ?)");
			pst.setInt(1, quantiteNouvelle);
			pst.setString(2, nomProduit);
			pst.setString(3, nomCatalogueProduit);
			pst.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(I_Produit produit) {
		String nomProduit = produit.getNom();
		try {
			PreparedStatement pst = connect.prepareStatement("DELETE FROM Produits WHERE nomProduit = ?");
			pst.setString(1, nomProduit);
			pst.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<I_Produit> findAll(String nomCatalogue) {
		List<I_Produit> listProduits = new ArrayList<I_Produit>();
		I_Produit p;
		String nomProduit;
		int qteStock;
		double prixHT;
		I_Catalogue catalogue;

		try {
			PreparedStatement pst = connect.prepareStatement(
					"SELECT nomProduit, quantiteStock, prixUnitaireHT, c.idCatalogue FROM Produits p JOIN Catalogue c ON p.idCatalogue = c.idCatalogue WHERE nomCatalogue = ?");
			pst.setString(1, nomCatalogue);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				nomProduit = rs.getString(1);
				qteStock = rs.getInt(2);
				prixHT = rs.getDouble(3);
				catalogue = this.findCatalogue(rs.getInt(4));
				p = new Produit(nomProduit, prixHT, qteStock, catalogue);
				listProduits.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduits;
	}

	@Override
	public I_Produit findByAttribute(String colonne, Object valeur) {
		String nomProduit;
		int qteStock;
		double prixHT;
		I_Catalogue catalogue;
		try {
			PreparedStatement pst = connect.prepareStatement(
					"SELECT nomProduit, quantiteStock, prixUnitaireHT, idCatalogue FROM Produits WHERE "+ colonne +" = ?");
			pst.setObject(1, valeur);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				nomProduit = rs.getString(1);
				qteStock = rs.getInt(2);
				prixHT = rs.getInt(3);
				catalogue = this.findCatalogue(rs.getInt(4));
				return new Produit(nomProduit, prixHT, qteStock, catalogue);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}

	private I_Catalogue findCatalogue(int idCatalogue) {
		String nomCatalogue;
		
		try {
			PreparedStatement pst = connect
					.prepareStatement("SELECT nomCatalogue FROM Catalogue WHERE idCatalogue = ?");
			pst.setInt(1, idCatalogue);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				nomCatalogue = rs.getString(1);
				I_Catalogue c = new Catalogue(nomCatalogue);
				return c;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param le
	 *            nom du catalogue concerné
	 * @return le nombre de produits contenus dans un catalogue
	 */
	@Override
	public int getNbTuples(String nomCatalogue) {
		try {
			PreparedStatement pst = connect.prepareStatement(
					"SELECT count(*) FROM Produits p JOIN Catalogue c ON c.idCatalogue = p.idCatalogue WHERE nomCatalogue = ?");
			pst.setString(1, nomCatalogue);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
