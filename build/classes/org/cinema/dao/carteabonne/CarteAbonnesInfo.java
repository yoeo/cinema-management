/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cinema.dao.carteabonne;

import java.sql.Date;

/**
 * 
 * @author Administrateur
 */
public class CarteAbonnesInfo
{
	private int id;
	private Date dateAchat;
	private String nom;
	private String prenom;
	private int age;
	private String email;
	public CarteAbonnesInfo(int id, Date dateAchat, String nom, String prenom,
			int age, String email)
	{
		super();
		this.id = id;
		this.dateAchat = dateAchat;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.email = email;
	}
        /*   constructeur sans id */
        public CarteAbonnesInfo(Date dateAchat, String nom, String prenom,
			int age, String email)
	{
		super();
		
		this.dateAchat = dateAchat;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.email = email;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public Date getDateAchat()
	{
		return dateAchat;
	}
	public void setDateAchat(Date dateAchat)
	{
		this.dateAchat = dateAchat;
	}
	public String getNom()
	{
		return nom;
	}
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	public String getPrenom()
	{
		return prenom;
	}
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
}
