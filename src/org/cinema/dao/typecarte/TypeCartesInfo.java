/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cinema.dao.typecarte;

/**
 * 
 * @author Administrateur
 */
public class TypeCartesInfo
{
	private int id;
	private String nom;
	private float tarif;
/**
 * constructeur avec id
 * @param id
 * @param nom
 * @param tarif
 */
	public TypeCartesInfo(int id, String nom, float tarif)
	{
		this.id = id;
		this.nom = nom;
		this.tarif = tarif;
	}
        /**
         * constructeur sans id
         * @param nom
         * @param tarif
         */
       public TypeCartesInfo( String nom, float tarif)
	{
		this.nom = nom;
		this.tarif = tarif;
	}


	/**
	 * @return the nom
	 */
	public String getNom()
	{
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * @return the tarif
	 */
	public float getTarif()
	{
		return tarif;
	}

	/**
	 * @param tarif
	 *            the tarif to set
	 */
	public void setTarif(float tarif)
	{
		this.tarif = tarif;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}
}
