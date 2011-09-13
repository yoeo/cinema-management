package org.cinema.dao.salle;

import org.cinema.model.ConnexionBD;
import org.cinema.utils.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SallesDao
{
	public List<SallesInfo> allSalle ()
	{
		List<SallesInfo> salles = new ArrayList<SallesInfo>();
		try
		{
			String sql = "SELECT * FROM SALLES";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			while (r.next())
			{
				salles.add(new SallesInfo(r.getInt("ID"), r.getString("NOM"), r.getInt("NBPLACE"), r.getBoolean("ESTTROISD")));
			}
		}
		catch (Exception e)
		{
			Log.log("Echec de récupération de toutes les salles " + e.getMessage());
		}
		return salles;
	}

	public boolean saveSalle (SallesInfo salle)
	{
		boolean ok = false;
		try
		{
			String sql = "INSERT INTO SALLES(NOM, NBPLACE, ESTTROISD)"
						+ " VALUES (?, ?, ?) ";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);

			ps.setString(1, salle.getNom());
			ps.setInt(2, salle.getNbPlace());
			ps.setBoolean(3, salle.isEstTroisD());

			ps.executeUpdate();
			
			sql = "SELECT MAX(ID) AS NEW_ID FROM SALLES";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			r.next();
			salle.setId(r.getInt("NEW_ID"));
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec d'enregistrement de la salle " + e.getMessage());
		}
		return ok;
	}

	public boolean updateSalle(SallesInfo salle)
	{
		boolean ok = false;
		try
		{
			String sql = "UPDATE SALLES SET NOM= ?, NBPLACE=? ,ESTTROISD=? WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setString(1, salle.getNom());
			ps.setInt(2, salle.getNbPlace());
			ps.setBoolean(3, salle.isEstTroisD());
			ps.setInt(4, salle.getId());

			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de modification de la salle " + e.getMessage());
		}
		return ok;
	}

	public boolean removeSalle(int id)
	{
		boolean ok = false;
		try
		{
			String sql = "DELETE FROM SALLES WHERE ID=?";
			// Create a Prepared statement
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de suppression de la salle " + e.getMessage());
		}
		return ok;
	}

	public SallesInfo getSalle(int id)
	{
		SallesInfo salle = null;
		try
		{
			String sql = "SELECT * FROM SALLES WHERE ID=?";
			// Create a Prepared statement
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet r = ps.executeQuery();
			r.next();
			salle = new SallesInfo(r.getInt("ID"),
					r.getString("NOM"),
					r.getInt("NBPLACE"),
					r.getBoolean("ESTTROISD"));
		}
		catch (Exception e)
		{
			Log.log("Echec de recuperation de la salle " + e.getMessage());
			e.printStackTrace();
		}
		return salle;
	}
}
