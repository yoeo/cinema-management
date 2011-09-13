package dao;

import java.sql.Date;
import java.sql.Time;

import junit.framework.Assert;

import org.cinema.model.ConnexionBD;
import org.cinema.dao.carteabonne.CarteAbonnesDao;
import org.cinema.dao.carteabonne.CarteAbonnesInfo;
import org.cinema.dao.film.FilmsDao;
import org.cinema.dao.film.FilmsInfo;
import org.cinema.dao.reservation.ReservationsDao;
import org.cinema.dao.reservation.ReservationsInfo;
import org.cinema.dao.salle.SallesDao;
import org.cinema.dao.salle.SallesInfo;
import org.cinema.dao.seance.SeancesDao;
import org.cinema.dao.seance.SeancesInfo;
import org.cinema.dao.typecarte.TypeCartesDao;
import org.cinema.dao.typecarte.TypeCartesInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestReservation
{
	ReservationsDao dao = null;
	ReservationsInfo element = null;

	@Before
	public void setUp() throws Exception
	{
		ConnexionBD.loadTables("mem:Cinema", null);
		dao = new ReservationsDao();
		
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
		new SeancesDao().saveSeance(new SeancesInfo(0, 1, 1, Date.valueOf("2011-01-21"), Time.valueOf("9:00:00"), true));
		new SeancesDao().saveSeance(new SeancesInfo(0, 1, 1, Date.valueOf("2011-01-21"), Time.valueOf("20:00:00"), true));
		new TypeCartesDao().saveTypeCarte(new TypeCartesInfo(0, "Plein Tarif", 29.99f));
		new CarteAbonnesDao().saveCarteAbonne(new CarteAbonnesInfo(0, Date.valueOf("2011-11-03"), "Kalya", "Michou", 13, "kami@lolali.bf"));
		
		element = new ReservationsInfo(0, 0, 1, 1);
	}
	@After
	public void tearDown() throws Exception
	{
		ConnexionBD.closeConnection();
	}

	@Test
	public void testSaveReservation()
	{		
		Assert.assertTrue(dao.saveReservation(element));
		Assert.assertTrue(dao.allReservation().size() == 1);
	}

	@Test
	public void testAllReservation()
	{
		Assert.assertTrue(dao.allReservation().size() == 0);
		dao.saveReservation(element);		
		Assert.assertTrue(dao.allReservation().size() == 1);
	}

	@Test
	public void testGetReservation()
	{
		dao.saveReservation(element);
		element = dao.allReservation().get(0);

		Assert.assertNotNull(dao.getReservation(element.getId()));
	}

	@Test
	public void testUpdateReservation()
	{
		dao.saveReservation(element);
		element = dao.allReservation().get(0);
		element.setSeanceId(2);

		Assert.assertTrue(dao.updateReservation(element));
		Assert.assertTrue(dao.allReservation().get(0).getSeanceId() == 2);
	}

	@Test
	public void testRemoveReservation()
	{
		dao.saveReservation(element);
		element = dao.allReservation().get(0);
		
		Assert.assertTrue(dao.removeReservation(element.getId()));
		Assert.assertTrue(dao.allReservation().size() == 0);
	}
}
