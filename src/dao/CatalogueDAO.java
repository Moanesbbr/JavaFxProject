package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import metier.Catalogue;
import metier.I_Catalogue;
public class CatalogueDAO implements I_DAO<I_Catalogue> {
	
	private Connection connect;
	
	public CatalogueDAO() {
		super();
		this.connect = null;
	}
	
	public CatalogueDAO(Connection connect) {
		super();
		this.connect = connect;
	}

	@Override
	public boolean create(I_Catalogue cat) {
		String nomCatalogue = cat.getNomCatalogue();
		
		try {
			CallableStatement cst = connect.prepareCall("{call nouveauCatalogue(?)}");
			cst.setString(1, nomCatalogue);
			cst.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean update(I_Catalogue cat){
		return false;
	}

	@Override
	public boolean delete(I_Catalogue cat) {
		String nomCat = cat.getNomCatalogue();
		try {
			PreparedStatement pst = connect.prepareStatement("DELETE FROM Catalogue WHERE nomCatalogue = ?");
			pst.setString(1, nomCat);
			pst.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<I_Catalogue> findAll(String nomObj) {
		List<I_Catalogue> listeCatalogue = new ArrayList<I_Catalogue>();
		I_Catalogue catalogue;
		String nomCatalogue;
		
		try {
			PreparedStatement pst = connect.prepareStatement("SELECT nomCatalogue FROM Catalogue");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				nomCatalogue = rs.getString(1);
				catalogue = new Catalogue(nomCatalogue);
				listeCatalogue.add(catalogue);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeCatalogue;
	}

	@Override
	public I_Catalogue findByAttribute(String colonne, Object valeur) {
		String nomCatalogue;
		try {
			PreparedStatement pst = connect.prepareStatement("SELECT nomCatalogue FROM Produits WHERE "+ colonne +" = ?");
			pst.setString(1, colonne);
			pst.setObject(2, valeur);
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
	 * @param le nom du catalogue concerné
	 * @return le nombre de produits contenus dans un catalogue
	 */
	@Override
	public int getNbTuples(String nomCatalogue){
		try {
			PreparedStatement pst = connect.prepareStatement(
					"SELECT count(*) FROM Catalogue c JOIN Produits p ON c.idCatalogue = p.idCatalogue WHERE nomCatalogue = ?");
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
