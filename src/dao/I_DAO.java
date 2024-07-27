package dao;

import java.util.List;

import metier.I_Produit;

public interface I_DAO<T> {

	/**
	 * Enregistre l'objet obj dans la BD
	 * @param obj
	 * @return TRUE ssi l'objet T a été créé
	 */
	public abstract boolean create(T obj);

	/**
	 * Modifie les données de l'objet obj dans la BD
	 * @param obj
	 * @return  TRUE ssi l'objet T a été mis à jour
	 */
	public abstract boolean update(T obj) throws QuantiteeStock_Exception;

	/**
	 * Supprime l'objet dont le nom = nomT dans la BD
	 * @param obj
	 * @return  TRUE ssi l'objet T a été supprimé 
	 */
	public abstract boolean delete(T obj);

	/**
	 * @param id
	 * @return une liste de T éléments contenu dans la BD
	 */
	public abstract List<T> findAll(String nomObj);

	/**
	 * @param colonne, valeur
	 * @return un objet T contenu dans la BD
	 */ 
	public abstract T findByAttribute(String colonne, Object valeur); 
	/**
	 * 
	 * @param nomCat
	 * @return le nombre de produits dans un catalogue
	 */
	public abstract int getNbTuples(String nom);
	
}
