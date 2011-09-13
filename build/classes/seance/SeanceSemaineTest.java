package seance;

import org.concordion.integration.junit3.ConcordionTestCase;
import org.cinema.dao.seance.SeancesDao;
import org.cinema.dao.seance.SeancesInfo;
import org.cinema.model.ConnexionBD;
import java.util.List;

public class SeanceSemaineTest extends ConcordionTestCase
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		ConnexionBD.loadTables("mem:Cinema", null);

		ConnexionBD.getConnection().createStatement()
			.executeUpdate("INSERT INTO FILMS (TITRE, DUREE)" +
			"VALUES ('Paris Potter', '1:52:13')");
		ConnexionBD.getConnection().createStatement()
			.executeUpdate("INSERT INTO SALLES (NOM, NBPLACE) " +
			"VALUES ('Stade De France', 40000)");
		ConnexionBD.getConnection().createStatement()
			.executeUpdate("INSERT INTO SEANCES (FILMID, SALLEID, DEBUT, HEURE, ESTTROISD) " +
			"VALUES (1, 1, CURDATE(), '12:08:02', FALSE)");
		ConnexionBD.getConnection().createStatement()
			.executeUpdate("INSERT INTO SEANCES (FILMID, SALLEID, DEBUT, HEURE, ESTTROISD) " +
			"VALUES (1, 1, DATEADD ('DAY', 8, NOW()), '12:08:30', FALSE)");
	}
	
    public String nbrSeance ()
    {
    	List<SeancesInfo> s = new SeancesDao().weekSeance();

    	int nb = s.size();
        return (nb == 1) ? "Une seance!" : "" + nb + " seances!";
    }
    
    @Override
    protected void tearDown() throws Exception
    {
    	// TODO Auto-generated method stub
    	super.tearDown();
    	ConnexionBD.closeConnection();
    }
}
