package dao;

import java.sql.Date;

import junit.framework.Assert;

import org.cinema.model.ConnexionBD;
import org.cinema.dao.carteabonne.CarteAbonnesDao;
import org.cinema.dao.carteabonne.CarteAbonnesInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCarteAbonne
{
	CarteAbonnesDao dao = null;
	CarteAbonnesInfo element = null;
	
	@Before
	public void setUp() throws Exception
	{
		ConnexionBD.loadTables("mem:Cinema", null);
		dao = new CarteAbonnesDao();
		element = new CarteAbonnesInfo(0, Date.valueOf("2011-11-03"), "Kalya", "Michou", 13, "kami@lolali.bf");
	}
	@After
	public void tearDown() throws Exception
	{
		ConnexionBD.closeConnection();
	}

	@Test
	public void testSaveCarteAbonne()
	{		
		Assert.assertTrue(dao.saveCarteAbonne(element));
		Assert.assertTrue(dao.allCarteAbonne().size() == 1);
	}

	@Test
	public void testAllCarteAbonne()
	{
		Assert.assertTrue(dao.allCarteAbonne().size() == 0);
		dao.saveCarteAbonne(element);		
		Assert.assertTrue(dao.allCarteAbonne().size() == 1);
	}

	@Test
	public void testGetCarteAbonne()
	{
		dao.saveCarteAbonne(element);
		element = dao.allCarteAbonne().get(0);

		Assert.assertNotNull(dao.getCarteAbonne(element.getId()));
	}

	@Test
	public void testUpdateCarteAbonne()
	{
		dao.saveCarteAbonne(element);
		element = dao.allCarteAbonne().get(0);
		element.setNom("Caid");
		
		Assert.assertTrue(dao.updateCarteAbonne(element));
		Assert.assertTrue(dao.allCarteAbonne().get(0).getNom()
				.equals("Caid"));
	}

	@Test
	public void testRemoveCarteAbonne()
	{
		dao.saveCarteAbonne(element);
		element = dao.allCarteAbonne().get(0);
		
		Assert.assertTrue(dao.removeCarteAbonne(element.getId()));
		Assert.assertTrue(dao.allCarteAbonne().size() == 0);
	}
}
