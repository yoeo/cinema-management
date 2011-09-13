package org.cinema.dao.salle;

public class SallesInfo
{
	public SallesInfo(int id, String nom, int nbPlace, boolean estTroisD)
	{
		this.id = id;
		this.nom = nom;
		this.nbPlace = nbPlace;
		this.estTroisD = estTroisD;
	}
        public SallesInfo(String nom, int nbPlace, boolean estTroisD)
	{
		this.nom = nom;
		this.nbPlace = nbPlace;
		this.estTroisD = estTroisD;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getNbPlace()
	{
		return nbPlace;
	}
	public void setNbPlace(int nbPlace)
	{
		this.nbPlace = nbPlace;
	}
	public boolean isEstTroisD()
	{
		return estTroisD;
	}
	public void setEstTroisD(boolean estTroisD)
	{
		this.estTroisD = estTroisD;
	}
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	public String getNom()
	{
		return nom;
	}
	private int id;
	private String nom;
	private int nbPlace;
	private boolean estTroisD;
}
