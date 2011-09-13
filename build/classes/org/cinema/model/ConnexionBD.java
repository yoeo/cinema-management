/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cinema.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.cinema.utils.Log;

/**
 * 
 * @author lynda
 */
public final class ConnexionBD
{

	//static String Protocol = "jdbc:derby:";
	private static String Protocol = "jdbc:h2:";
	private static String DataBase = "./tools/bd/Cinema;create=true";
	private static Connection con = null;

	private ConnexionBD()
	{}

	public static Connection getConnection() throws InstantiationException,
			IllegalAccessException
	{
		if (con == null)
		{
			try
			{
				// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				//Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
				Class.forName("org.h2.Driver");
				//Log.logInfo("driver trouvé");

			}
			catch (java.lang.ClassNotFoundException e)
			{
				Log.log("ClassNotFoundException: " + e.getMessage());
			}

			try
			{
				con = DriverManager.getConnection(Protocol + DataBase);
			}
			catch (SQLException ex)
			{
				Log.log("connexion echoué");
			}
		}
		return con;
	}

	public static void loadTables(String database,  String sqlFile) throws IOException, SQLException, InstantiationException, IllegalAccessException
	{
		if (database != null) { DataBase = database; }
		if (sqlFile == null) { sqlFile = "./tools/sql/tables.sql"; }
		
		closeConnection();
		BufferedReader r = null;
		String listTable = "";
		r = new BufferedReader(new FileReader(sqlFile));
		while (r.ready())
		{
			listTable += r.readLine();
		}
		for (String t : listTable.split(";"))
		{
			ConnexionBD.getConnection().createStatement().executeUpdate(t);
		}
		r.close();
	}

	public static void closeConnection () throws SQLException
	{
		if (con != null)
		{
			con.close();
			con = null;
		}
	}
}
