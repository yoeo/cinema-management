/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cinema.utils;

/**
 *
 * @author ydeo
 */
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log
{
	private static String fichier = "log.txt";
	private static int logSortie = 0;

	private Log() {}

	public static void log (String msg)
	{
		switch (logSortie)
		{
			case 0:
				logConsole (msg);
				break;
			case 1:
				logFichier (msg);
				break;
			case 2:
				logSysteme (msg);
				break;
			case 3:
				logConsole (msg);
				logFichier (msg);
		}
	}

	public static void setOutput (int s)
	{
		logSortie = (s >= 0 && s <= 3) ? s : logSortie;
	}

	public static void logInfo (String msg)
	{
		Log.log("-- " + msg);
	}

	private static void logConsole(String msg)
	{
		System.out.println(msg);
	}

	private static void logFichier(String msg)
	{
		try
		{
			PrintWriter f = new PrintWriter(new FileWriter (fichier, true));
			f.println (msg);
			f.close();
		}
		catch (Exception _)
		{
			_.printStackTrace();
		}
	}

	private static void logSysteme(String msg)
	{
		Logger.getLogger(Log.class.getName()).log(Level.SEVERE, msg);
	}
}
