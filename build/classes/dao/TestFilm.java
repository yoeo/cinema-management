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

public class TestFilm
{
	FilmsDao dao = null;
	FilmsInfo element = null;
	
	@Before
	public void setUp() throws Exception
	{
		ConnexionBD.loadTables("mem:Cinema", null);
		dao = new FilmsDao();
		element = new FilmsInfo(
				0,
				"Printemps",
				Time.valueOf("1:12:3"),
				"Une histoire d'amour",
				Date.valueOf("2007-10-21"),
				"John Bash, Carrie Bradshow",
				0.3f,
				"print.jpg"
				);
	}

	@After
	public void tearDown() throws Exception
	{
		ConnexionBD.closeConnection();
	}

	@Test
	public void testSaveFilm()
	{		
		Assert.assertTrue(dao.saveFilm(element));
		Assert.assertTrue(dao.allFilm().size() == 1);
	}

	@Test
	public void testAllFilm()
	{
		Assert.assertTrue(dao.allFilm().size() == 0);
		dao.saveFilm(element);		
		Assert.assertTrue(dao.allFilm().size() == 1);
	}


	@Test
	public void testWeekFilm()
	{
		Assert.assertTrue(dao.weekFilm().size() == 0);
		dao.saveFilm(element);		
		new SallesDao().saveSalle(new SallesInfo(0, "Beaux Arts", 257, true));
		new SeancesDao().saveSeance(new SeancesInfo(1, 1, new Date(System.currentTimeMillis()), Time.valueOf("18:00:00"), true));
		Assert.assertTrue(dao.weekFilm().size() == 1);
	}

	@Test
	public void testGetFilm()
	{
		dao.saveFilm(element);
		element = dao.allFilm().get(0);

		Assert.assertNotNull(dao.getFilm(element.getId()));
	}

	@Test
	public void testUpdateFilm()
	{
		dao.saveFilm(element);
		element = dao.allFilm().get(0);
		element.setTitre("Caid");
		
		Assert.assertTrue(dao.updateFilm(element));
		Assert.assertTrue(dao.allFilm().get(0).getTitre()
				.equals("Caid"));
	}

	@Test
	public void testRemoveFilm()
	{
		dao.saveFilm(element);
		element = dao.allFilm().get(0);
		
		Assert.assertTrue(dao.removeFilm(element.getId()));
		Assert.assertTrue(dao.allFilm().size() == 0);
	}
}
