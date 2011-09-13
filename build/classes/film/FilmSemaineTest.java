/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package film;

import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.concordion.integration.junit3.ConcordionTestCase;
import org.cinema.dao.film.FilmsDao;
import org.cinema.model.ConnexionBD;
import java.util.List;
import org.cinema.dao.film.FilmsInfo;
import org.cinema.dao.salle.SallesDao;
import org.cinema.dao.salle.SallesInfo;
import org.cinema.dao.seance.SeancesDao;
import org.cinema.dao.seance.SeancesInfo;

/**
 *
 * @author med
 */
public class FilmSemaineTest extends ConcordionTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ConnexionBD.loadTables("mem:Cinema", null);
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous 1", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2010-02-02"), "Ben Stiller", 45, "img1"));
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous 2", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2010-02-05"), "Ben Stiller", 45, "img1"));
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous 3", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2010-06-14"), "Ben Stiller", 45, "img1"));

        new SallesDao().saveSalle(new SallesInfo(0,"Carlie S1", 104, true));

        new SeancesDao().saveSeance(new SeancesInfo(0, 1, 1, new Date(System.currentTimeMillis()), Time.valueOf("02:30:33"), true));          
        new SeancesDao().saveSeance(new SeancesInfo(0, 2, 1, new Date(System.currentTimeMillis()), Time.valueOf("02:30:33"), true));          
    }

    public String nbrFilmsSemaine() {
        List<FilmsInfo> s = null;
        try {
            s = new FilmsDao().weekFilm(); 
        } catch (Exception ex) {
            Logger.getLogger(FilmSemaineTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        int nb = s.size();
        return (nb == 2) ? "deux films!" : "" + nb + " films!";
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
        ConnexionBD.closeConnection();
    }
}
