/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cinema.dao.reservation;

import org.cinema.model.ConnexionBD;
import org.cinema.utils.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ReservationsDao
{
	public boolean saveReservation(ReservationsInfo reservation)
	{
		boolean ok = false;
		try
		{
			String sql = "INSERT INTO RESERVATIONS(SEANCEID, CARTEABONNEID, TYPECARTEID) VALUES (?,?,?) ";
			PreparedStatement ps;
			ps = ConnexionBD.getConnection().prepareStatement(sql);
			ps.setInt(1, reservation.getSeanceId());
			if (reservation.getCarteAbonneId() == 0) ps.setNull(2, java.sql.Types.INTEGER);
			else ps.setInt(2, reservation.getCarteAbonneId());

			if (reservation.getTypeCarteId() == 0) ps.setNull(3, java.sql.Types.INTEGER);
			else ps.setInt(3, reservation.getTypeCarteId());

			ps.executeUpdate();
			
			sql = "SELECT MAX(ID) AS NEW_ID FROM RESERVATIONS";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			r.next();
			reservation.setId(r.getInt("NEW_ID"));
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec d'enregistrement de la salle " + e.getMessage());
		}
		return ok;
	}

	public List<ReservationsInfo> allReservation ()
	{
		List<ReservationsInfo> reservations = new ArrayList<ReservationsInfo>();
		try
		{
			String sql = "SELECT * FROM RESERVATIONS";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			while (r.next())
			{
				reservations.add(new ReservationsInfo(r.getInt("ID"), r.getInt("CARTEABONNEID"), r.getInt("TYPECARTEID"), r.getInt("SEANCEID")));
			}
		}
		catch (Exception e)
		{
			Log.log("Echec de recuperation de toutes les reservation " + e.getMessage());
		}
		return reservations;
	}

	public boolean updateReservation(ReservationsInfo reservation)
	{
		boolean ok = false;
		try
		{
			String sql = "UPDATE RESERVATIONS SET SEANCEID=?, CARTEABONNEID=?, TYPECARTEID=? WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, reservation.getSeanceId());
			if (reservation.getCarteAbonneId() == 0) ps.setNull(2, java.sql.Types.INTEGER);
			else ps.setInt(2, reservation.getCarteAbonneId());
			ps.setInt(3, reservation.getTypeCarteId());
			ps.setInt(4, reservation.getId());

			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de modification de la reservation " + e.getMessage());
		}
		return ok;
	}

	public boolean removeReservation(int id)
	{
		boolean ok = false;
		try
		{
			String sql = "DELETE FROM RESERVATIONS WHERE ID=?";
			// Create a Prepared statement
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de suppression de la reservation " + e.getMessage());
		}
		return ok;
	}

	public ReservationsInfo getReservation(int id)
	{
		ReservationsInfo reservation = null;
		try
		{
			String sql = "SELECT * FROM RESERVATIONS WHERE ID=?";
			// Create a Prepared statement
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet r = ps.executeQuery();
			r.next();
			reservation = new ReservationsInfo(r.getInt("ID"), r.getInt("CARTEABONNEID"), r.getInt("TYPECARTEID"), r.getInt("SEANCEID"));
		}
		catch (Exception e)
		{
			Log.log("Echec de suppression de la reservation " + e.getMessage());
		}
		return reservation;
	}

}
