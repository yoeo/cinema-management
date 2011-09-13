/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cinema.dao.carteabonne;

import org.cinema.model.ConnexionBD;
import org.cinema.utils.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 
 * @author Administrateur
 */
public class CarteAbonnesDao
{
	public boolean saveCarteAbonne(CarteAbonnesInfo carte)
	{
		boolean ok = false;
		try
		{
			String sql = "INSERT INTO CARTEABONNES(NOM ,PRENOM ,EMAIL,DATEACHAT,AGE) VALUES (?,?,?,?,?)";

			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);

			ps.setString(1, carte.getNom());
			ps.setString(2, carte.getPrenom());
			ps.setString(3, carte.getEmail());
			ps.setDate(4, carte.getDateAchat());
			ps.setInt(5, carte.getAge());
			
			ps.executeUpdate();
			
			sql = "SELECT MAX(ID) AS NEW_ID FROM CARTEABONNES";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			r.next();
			carte.setId(r.getInt("NEW_ID"));
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec d'enregistrement de la carte d'abonne " + e.getMessage());
		}
		return ok;
	}

	public boolean updateCarteAbonne(CarteAbonnesInfo carte)
	{
		boolean ok = false;
		try
		{
			String sql = "UPDATE CARTEABONNES SET NOM=?,PRENOM=?,EMAIL=?, DATEACHAT=? ,AGE=? WHERE ID =?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);

			ps.setString(1, carte.getNom());
			ps.setString(2, carte.getPrenom());
			ps.setString(3, carte.getEmail());
			ps.setDate(4, carte.getDateAchat());
			ps.setInt(5, carte.getAge());
			ps.setInt(6, carte.getId());

			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de modification de la carte d'abonne " + e.getMessage());
		}
		return ok;
	}

	public boolean removeCarteAbonne(int id)
	{
		boolean ok = false;
		try
		{
			String sql = "DELETE FROM CARTEABONNES WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return ok;
	}
	
	public ArrayList<CarteAbonnesInfo> allCarteAbonne ()
	{
		ArrayList<CarteAbonnesInfo> carteList = new ArrayList<CarteAbonnesInfo>();
		try
		{
			String sql = "SELECT * FROM CARTEABONNES";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			while (r.next())
			{
				carteList.add(new CarteAbonnesInfo (r.getInt("ID"), r.getDate("DATEACHAT"),
						r.getString("NOM"), r.getString("PRENOM"), r.getInt("AGE"), r.getString("EMAIL")));
			}
		}
		catch (Exception e)
		{
			Log.log("Echec de recuperation de toutes les cartes d'abonnes  " + e.getMessage());
		}
		return carteList;
	}

	public CarteAbonnesInfo getCarteAbonne (int id)
	{
		CarteAbonnesInfo carteList = null;
		try
		{
			String sql = "SELECT * FROM CARTEABONNES WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet r = ps.executeQuery();
			r.next();

			carteList = new CarteAbonnesInfo (r.getInt("ID"), r.getDate("DATEACHAT"),
						r.getString("NOM"), r.getString("PRENOM"), r.getInt("AGE"), r.getString("EMAIL"));
		}
		catch (Exception e)
		{
			Log.log("Echec de recuperation de la carte d'abonne  " + e.getMessage());
		}
		return carteList;
	}


}
