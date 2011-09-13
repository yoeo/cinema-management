/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cinema.dao.film;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author lynda
 */

public class FilmsInfo
{

	private int id;
	private String titre;
	private Time duree;
	private String resume;
	private Date dateSortie;
	private String acteur;
	private float popularite;
	private String image;
	public FilmsInfo(int id, String titre, Time duree, String resume,
			Date dateSortie, String acteur, float popularite, String image)
	{
		super();
		this.id = id;
		this.titre = titre;
		this.duree = duree;
		this.resume = resume;
		this.dateSortie = dateSortie;
		this.acteur = acteur;
		this.popularite = popularite;
		this.image = image;
	}
        public FilmsInfo(String titre, Time duree, String resume,
			Date dateSortie, String acteur, float popularite, String image)
	{
		super();
		
		this.titre = titre;
		this.duree = duree;
		this.resume = resume;
		this.dateSortie = dateSortie;
		this.acteur = acteur;
		this.popularite = popularite;
		this.image = image;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getTitre()
	{
		return titre;
	}
	public void setTitre(String titre)
	{
		this.titre = titre;
	}
	public Time getDuree()
	{
		return duree;
	}
	public void setDuree(Time duree)
	{
		this.duree = duree;
	}
	public String getResume()
	{
		return resume;
	}
	public void setResume(String resume)
	{
		this.resume = resume;
	}
	public Date getDateSortie()
	{
		return dateSortie;
	}
	public void setDateSortie(Date dateSortie)
	{
		this.dateSortie = dateSortie;
	}
	public String getActeur()
	{
		return acteur;
	}
	public void setActeur(String acteur)
	{
		this.acteur = acteur;
	}
	public float getPopularite()
	{
		return popularite;
	}
	public void setPopularite(float popularite)
	{
		this.popularite = popularite;
	}
	public String getImage()
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
}
