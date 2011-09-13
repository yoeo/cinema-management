package dao;

import junit.framework.Assert;

import org.cinema.model.ConnexionBD;
import org.cinema.dao.salle.SallesDao;
import org.cinema.dao.salle.SallesInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSalle
{
	SallesDao dao = null;
	SallesInfo element = null;
	
	@Before
	public void setUp() throws Exception
	{
		ConnexionBD.loadTables("mem:Cinema", null);
		dao = new SallesDao();
		element = new SallesInfo(0, "Beaux Arts", 257, true);
	}

	@After
	public void tearDown() throws Exception
	{
		ConnexionBD.closeConnection();
	}

	@Test
	public void testSaveSalle()
	{		
		Assert.assertTrue(dao.saveSalle(element));
		Assert.assertTrue(dao.allSalle().size() == 1);
	}

	@Test
	public void testAllSalle()
	{
		Assert.assertTrue(dao.allSalle().size() == 0);
		dao.saveSalle(element);		
		Assert.assertTrue(dao.allSalle().size() == 1);
	}

	@Test
	public void testGetSalle()
	{
		dao.saveSalle(element);
		element = dao.allSalle().get(0);

		Assert.assertNotNull(dao.getSalle(element.getId()));
	}

	@Test
	public void testUpdateSalle()
	{
		dao.saveSalle(element);
		element = dao.allSalle().get(0);
		element.setNom("Caid");
		
		Assert.assertTrue(dao.updateSalle(element));
		Assert.assertTrue(dao.allSalle().get(0).getNom()
				.equals("Caid"));
	}

	@Test
	public void testRemoveSalle()
	{
		dao.saveSalle(element);
		element = dao.allSalle().get(0);
		
		Assert.assertTrue(dao.removeSalle(element.getId()));
		Assert.assertTrue(dao.allSalle().size() == 0);
	}
}
