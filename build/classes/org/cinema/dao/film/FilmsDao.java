/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cinema.dao.film;

import org.cinema.model.ConnexionBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.cinema.utils.Log;

/**
 * 
 * @author lynda
 */
public class FilmsDao {

    public boolean saveFilm(FilmsInfo film)
    {
        try {
            String sql = "INSERT INTO FILMS(TITRE, DUREE, RESUME, "
                    + "DATESORTIE, ACTEUR,POPULARITE,IMAGE) VALUES (?,?,?,?,?,?,?) ";

            // Create a Preparedstatement
            PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(sql);
            ps.setString(1, film.getTitre());
            ps.setTime(2, film.getDuree());
            ps.setString(3, film.getResume());
            ps.setDate(4, film.getDateSortie());
            ps.setString(5, film.getActeur());
            ps.setFloat(6, film.getPopularite());
            ps.setString(7, film.getImage());

            ps.executeUpdate();
            
			sql = "SELECT MAX(ID) AS NEW_ID FROM FILMS";
			ResultSet r = ConnexionBD.getConnection()
				.createStatement().executeQuery(sql);

			r.next();
			film.setId(r.getInt("NEW_ID"));
            return true;
        } catch (Exception e) {
            Log.log("Echec de sauvegarde du film : " + e.getMessage());
            return false;
        }
    }

    /**
     * modification d'un film donnée.
     * 
     * @param film
     */
    public boolean updateFilm(FilmsInfo film) {
        try {
            String sql = "UPDATE FILMS SET TITRE = ?, DUREE=? ,RESUME=?, DATESORTIE=? , ACTEUR=?, POPULARITE=? , IMAGE =? WHERE ID=?";
            // Create a Preparedstatement
            PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(sql);

            ps.setString(1, film.getTitre());
            ps.setTime(2, film.getDuree());
            ps.setString(3, film.getResume());
            ps.setDate(4, film.getDateSortie());
            ps.setString(5, film.getActeur());
            ps.setFloat(6, film.getPopularite());
            ps.setString(7, film.getImage());
            ps.setInt(8, film.getId());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            Log.log("Echec de modification d'un film : " + e.getMessage());
            return false;
        }
    }

    public boolean removeFilm(int id) {
        
        try {
            String sql = "DELETE FROM  FILMS  WHERE ID = ?";
            // Create a Prepared statement
            PreparedStatement ps = ConnexionBD.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            Log.log("Echec de suppression d'un film : " + e.getMessage());
            return false;
        }        
    }

    // recuperer la list des films du semaine
    public List<FilmsInfo> weekFilm() {

        List<FilmsInfo> films = new ArrayList<FilmsInfo>();
        try {

            String sql = "SELECT * FROM FILMS "
                    + "WHERE ID IN (" +
                    		"SELECT FILMID FROM SEANCES WHERE (DEBUT >= "
                    + " CURDATE()) AND (DEBUT < DATEADD ('DAY', 7, NOW())))";
            //String sql = "SELECT * FROM FILMS";
            ResultSet r = ConnexionBD.getConnection().createStatement().executeQuery(sql);
            while (r.next()) {
                films.add(new FilmsInfo
                		(r.getInt("id"),
                        		r.getString("titre"),
                        		r.getTime("duree"),
                        		r.getString("resume"),
                                r.getDate("dateSortie"),
                                r.getString("acteur"),
                                r.getFloat("popularite"),
                                r.getString("image")));
            }
        } catch (Exception e) {
            Log.log("Echec de recuperation de tous les films d'une semaine " + e.getMessage());
        }
        return films;
    }

    // recuperer la list des films de deux mois 
    public List<FilmsInfo> monthFilm() {

        List<FilmsInfo> films = new ArrayList<FilmsInfo>();
        try {

            String sql = "SELECT * FROM FILMS "
                    + "WHERE (DATESORTIE >= "
                    + " CURDATE()) AND (DATESORTIE < DATEADD ('MM', 2, NOW()))";
            ResultSet r = ConnexionBD.getConnection().createStatement().executeQuery(sql);
            
            while (r.next()) {
                films.add(new FilmsInfo
                		(r.getInt("id"),
                		r.getString("titre"),
                		r.getTime("duree"),
                		r.getString("resume"),
                        r.getDate("dateSortie"),
                        r.getString("acteur"),
                        r.getFloat("popularite"),
                        r.getString("image")));
            }
        } catch (Exception e) {
            Log.log("Echec de recuperation de tous les films de deux mois " + e.getMessage());
        }
        return films;
    }


    // recuperer la list des films de deux mois 
    public List<FilmsInfo> allFilm() {

        List<FilmsInfo> films = new ArrayList<FilmsInfo>();
        try {

            String sql = "SELECT * FROM FILMS ";
            ResultSet r = ConnexionBD.getConnection().createStatement().executeQuery(sql);
            
            while (r.next()) {
                films.add(new FilmsInfo
                		(r.getInt("id"),
                        		r.getString("titre"),
                        		r.getTime("duree"),
                        		r.getString("resume"),
                                r.getDate("dateSortie"),
                                r.getString("acteur"),
                                r.getFloat("popularite"),
                                r.getString("image")));
            }
        } catch (Exception e) {
            Log.log("Echec de recuperation de tous les films de deux mois " + e.getMessage());
        }
        return films;
    }

    // recuperer la list des films de deux mois 
    public FilmsInfo getFilm(int id) {

        FilmsInfo film = null;
        try {

            String sql = "SELECT * FROM FILMS WHERE ID=?";
			PreparedStatement ps = ConnexionBD.getConnection()
				.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet r = ps.executeQuery();
            while (r.next()) {
                film = new FilmsInfo
                		(r.getInt("id"),
                		r.getString("titre"),
                		r.getTime("duree"),
                		r.getString("resume"),
                        r.getDate("dateSortie"),
                        r.getString("acteur"),
                        r.getFloat("popularite"),
                        r.getString("image"));
            }
        } catch (Exception e) {
            Log.log("Echec de recuperation d'un film " + e.getMessage());
        }
        return film;
    }
}
