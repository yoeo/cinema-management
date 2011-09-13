package org.cinema.dao.seance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.cinema.model.ConnexionBD;
import org.cinema.utils.Log;

public class SeancesDao
{
	public List<SeancesInfo> allSeance ()
	{
		List<SeancesInfo> seances = new ArrayList<SeancesInfo>();
		try
		{
			String sql = "SELECT * FROM SEANCES";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			while (r.next())
			{
				seances.add(new SeancesInfo(r.getInt("ID"), r.getInt("FILMID"), r.getInt("SALLEID"), r.getDate("DEBUT"), r.getTime("HEURE"), r.getBoolean("ESTTROISD")));
			}
		}
		catch (Exception e)
		{
			Log.log("Echec de recuperation de toutes les seance " + e.getMessage());
		}
		return seances;
	}

	public List<SeancesInfo> weekSeance ()
	{
		List<SeancesInfo> seances = new ArrayList<SeancesInfo>();
		try
		{
			String sql = "SELECT * FROM SEANCES " +
				"WHERE (DEBUT >= CURDATE()) AND (DEBUT < DATEADD ('DAY', 7, NOW()))";
			PreparedStatement ps = ConnexionBD.getConnection()
				.prepareStatement(sql);
			ResultSet r = ps.executeQuery();

			while (r.next())
			{
				seances.add(new SeancesInfo(r.getInt("ID"), r.getInt("FILMID"), r.getInt("SALLEID"), r.getDate("DEBUT"), r.getTime("HEURE"), r.getBoolean("ESTTROISD")));
			}
		}
		catch (Exception e)
		{
			Log.log("Echec de récupération de toutes les seance de la semaine " + e.getMessage());
		}
		return seances;
	}

	public boolean saveSeance (SeancesInfo seance)
	{
		boolean ok = false;
		try
		{
			String sql = "INSERT INTO SEANCES(FILMID, SALLEID, DEBUT, HEURE, ESTTROISD)"
						+ " VALUES (?, ?, ?, ?, ?) ";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);

			ps.setInt(1, seance.getFilmid());
			ps.setInt(2, seance.getSalleid());
			ps.setDate(3, seance.getDebut());
			ps.setTime(4, seance.getHeure());
			ps.setBoolean(5, seance.isEstTroisD());

			ps.executeUpdate();
			
			sql = "SELECT MAX(ID) AS NEW_ID FROM SEANCES";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			r.next();
			seance.setId(r.getInt("NEW_ID"));
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec d'enregistrement de la seance " + e.getMessage());
		}
		return ok;
	}

	public boolean updateSeance(SeancesInfo seance)
	{
		boolean ok = false;
		try
		{
			String sql = "UPDATE SEANCES SET FILMID= ?, SALLEID=?, DEBUT=?, ESTTROISD=?, HEURE=? WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, seance.getFilmid());
			ps.setInt(2, seance.getSalleid());
			ps.setDate(3, seance.getDebut());
			ps.setBoolean(4, seance.isEstTroisD());
			ps.setTime(5, seance.getHeure());
			ps.setInt(6, seance.getId());

			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de modification de la seance " + e.getMessage());
		}
		return ok;
	}

	public boolean removeSeance(int id)
	{
		boolean ok = false;
		try
		{
			String sql = "DELETE FROM SEANCES WHERE ID=?";
			// Create a Prepared statement
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de suppression de la seance " + e.getMessage());
		}
		return ok;
	}

	public SeancesInfo getSeance(int id)
	{
		SeancesInfo seance = null;
		try
		{
			String sql = "SELECT * FROM SEANCES WHERE ID=?";
			// Create a Prepared statement
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet r = ps.executeQuery();
			r.next();
			seance = new SeancesInfo(r.getInt("ID"), r.getInt("FILMID"), r.getInt("SALLEID"), r.getDate("DEBUT"), r.getTime("HEURE"), r.getBoolean("ESTTROISD"));
		}
		catch (Exception e)
		{
			Log.log("Echec de suppression de la seance " + e.getMessage());
		}
		return seance;
	}
	
}
