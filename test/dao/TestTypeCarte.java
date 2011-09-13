package dao;

import junit.framework.Assert;

import org.cinema.model.ConnexionBD;
import org.cinema.dao.typecarte.TypeCartesDao;
import org.cinema.dao.typecarte.TypeCartesInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTypeCarte
{
	TypeCartesDao dao = null;
	TypeCartesInfo element = null;
	
	@Before
	public void setUp() throws Exception
	{
		ConnexionBD.loadTables("mem:Cinema", null);
		dao = new TypeCartesDao();
		element = new TypeCartesInfo(0, "Carte Vieux", 29.99f);
	}
	@After
	public void tearDown() throws Exception
	{
		ConnexionBD.closeConnection();
	}

	@Test
	public void testSaveTypeCarte()
	{		
		Assert.assertTrue(dao.saveTypeCarte(element));
		Assert.assertTrue(dao.allTypeCarte().size() == 1);
	}

	@Test
	public void testAllTypeCarte()
	{
		Assert.assertTrue(dao.allTypeCarte().size() == 0);
		dao.saveTypeCarte(element);		
		Assert.assertTrue(dao.allTypeCarte().size() == 1);
	}

	@Test
	public void testGetTypeCarte()
	{
		dao.saveTypeCarte(element);
		element = dao.allTypeCarte().get(0);

		Assert.assertNotNull(dao.getTypeCarte(element.getId()));
	}

	@Test
	public void testUpdateTypeCarte()
	{
		dao.saveTypeCarte(element);
		element = dao.allTypeCarte().get(0);
		element.setNom("Caid");
		
		Assert.assertTrue(dao.updateTypeCarte(element));
		Assert.assertTrue(dao.allTypeCarte().get(0).getNom()
				.equals("Caid"));
	}

	@Test
	public void testRemoveTypeCarte()
	{
		dao.saveTypeCarte(element);
		element = dao.allTypeCarte().get(0);
		
		Assert.assertTrue(dao.removeTypeCarte(element.getId()));
		Assert.assertTrue(dao.allTypeCarte().size() == 0);
	}
}
