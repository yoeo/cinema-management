/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cinema.dao.typecarte;

import org.cinema.model.ConnexionBD;
import org.cinema.utils.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Administrateur
 */
public class TypeCartesDao
{
	public boolean saveTypeCarte(TypeCartesInfo type)
	{
		boolean ok = false;
		try
		{

			String sql = "INSERT INTO TYPECARTES(NOM, TARIF) VALUES (?,?)";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);

			ps.setString(1, type.getNom());
			ps.setFloat(2, type.getTarif());

			ps.executeUpdate();
			
			sql = "SELECT MAX(ID) AS NEW_ID FROM TYPECARTES";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			r.next();
			type.setId(r.getInt("NEW_ID"));
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de la sauvegarde du type de carte " + e.getMessage());
		}
		return ok;
	}

	public boolean updateTypeCarte(TypeCartesInfo type)
	{
		boolean ok = false;
		try
		{
			String sql = "UPDATE TYPECARTES SET NOM=?, TARIF=? WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);

			ps.setString(1, type.getNom());
			ps.setFloat(2, type.getTarif());
			ps.setInt(3, type.getId());
			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de modification du type de carte " + e.getMessage());
		}
		return ok;
	}

	public boolean removeTypeCarte(int id)
	{
		boolean ok = false;
		try
		{
			String sql = "DELETE FROM TYPECARTES WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ok = true;
		}
		catch (Exception e)
		{
			Log.log("Echec de la suppression du type de carte " + e.getMessage());
		}
		return ok;
	}

	public List<TypeCartesInfo> allTypeCarte ()
	{
	List<TypeCartesInfo> typeCartes = new ArrayList<TypeCartesInfo>();
	try
	{
		String sql = "SELECT * FROM TYPECARTES";
		ResultSet r = ConnexionBD.getConnection()
			.createStatement().executeQuery(sql);

		while (r.next())
		{
			typeCartes.add(new TypeCartesInfo(r.getInt("ID"), r.getString("NOM"), r.getFloat("TARIF")));
		}
	}
	catch (Exception e)
	{
		Log.log("Echec de recuperation de toutes les typeCarte " + e.getMessage());
	}
	return typeCartes;
}

	public TypeCartesInfo getTypeCarte(int id)
	{
		TypeCartesInfo typeCarte = null;
		try
		{
			String sql = "SELECT * FROM TYPECARTES WHERE ID=?";
			// Create a Prepared statement
			PreparedStatement ps = ConnexionBD.getConnection()
					.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet r = ps.executeQuery();
			r.next();
			typeCarte = new TypeCartesInfo(r.getInt("ID"), r.getString("NOM"), r.getFloat("TARIF"));
		}
		catch (Exception e)
		{
			Log.log("Echec de recuperation de la typeCarte " + e.getMessage());
		}
		return typeCarte;
	}

}
