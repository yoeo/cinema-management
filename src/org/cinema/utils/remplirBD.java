package org.cinema.utils;
import java.io.IOException;
import java.sql.SQLException;

import org.cinema.model.ConnexionBD;


public class remplirBD
{

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, SQLException, InstantiationException, IllegalAccessException
	{
		ConnexionBD.loadTables(null, null);
		ConnexionBD.loadTables(null,"./tools/sql/typecartes.sql");
		ConnexionBD.loadTables(null,"./tools/sql/films.sql");
		ConnexionBD.loadTables(null,"./tools/sql/carteabonnes.sql");
		ConnexionBD.loadTables(null,"./tools/sql/salles.sql");
		ConnexionBD.loadTables(null,"./tools/sql/seances.sql");
		ConnexionBD.loadTables(null,"./tools/sql/reservations.sql");
		Log.log("Base de donnee regeneree");
	}

}
