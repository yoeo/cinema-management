package dao;

import java.sql.Date;
import java.sql.Time;

import junit.framework.Assert;

import org.cinema.model.ConnexionBD;
import org.cinema.dao.film.FilmsDao;
import org.cinema.dao.film.FilmsInfo;
import org.cinema.dao.salle.SallesDao;
import org.cinema.dao.salle.SallesInfo;
import org.cinema.dao.seance.SeancesDao;
import org.cinema.dao.seance.SeancesInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSeance
{
	SeancesDao dao = null;
	SeancesInfo element = null;
	
	@Before
	public void setUp() throws Exception
	{
		ConnexionBD.loadTables("mem:Cinema", null);
		dao = new SeancesDao();
		new SallesDao().saveSalle(new SallesInfo(0, "Beaux Arts", 257, true));
		new FilmsDao().saveFilm(new FilmsInfo(
				0,
				"Printemps",
				Time.valueOf("1:12:3"),
				"Une histoire d'amour",
				Date.valueOf("2007-10-21"),
				"John Bash, Carrie Bradshow",
				0.3f,
				"print.jpg"
				));
		element = new SeancesInfo(0, 1, 1, Date.valueOf("2011-01-21"), Time.valueOf("9:12:11"), true);
	}
	@After
	public void tearDown() throws Exception
	{
		ConnexionBD.closeConnection();
	}

	@Test
	public void testSaveSeance()
	{		
		Assert.assertTrue(dao.saveSeance(element));
		Assert.assertTrue(dao.allSeance().size() == 1);
	}

	@Test
	public void testAllSeance()
	{
		Assert.assertTrue(dao.allSeance().size() == 0);
		dao.saveSeance(element);		
		Assert.assertTrue(dao.allSeance().size() == 1);
	}

	@Test
	public void testGetSeance()
	{
		dao.saveSeance(element);
		element = dao.allSeance().get(0);

		Assert.assertNotNull(dao.getSeance(element.getId()));
	}

	@Test
	public void testUpdateSeance()
	{
		dao.saveSeance(element);
		element = dao.allSeance().get(0);
		element.setEstTroisD(false);
		
		Assert.assertTrue(dao.updateSeance(element));
		Assert.assertTrue(dao.allSeance().get(0).isEstTroisD() == false);
	}

	@Test
	public void testRemoveSeance()
	{
		dao.saveSeance(element);
		element = dao.allSeance().get(0);
		
		Assert.assertTrue(dao.removeSeance(element.getId()));
		Assert.assertTrue(dao.allSeance().size() == 0);
	}
}
