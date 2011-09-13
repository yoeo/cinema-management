/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package film;

import java.sql.Date;
import java.sql.Time;
import org.concordion.integration.junit3.ConcordionTestCase;
import org.cinema.model.ConnexionBD;
import java.util.List;
import org.cinema.dao.film.*;

/**
 *
 * @author med
 */
public class FilmDeuxMoisTest extends ConcordionTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ConnexionBD.loadTables("mem:Cinema", null);
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2010-02-02"), "Ben Stiller", 45, "img1"));
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2011-03-07"), "Ben Stiller", 45, "img1"));
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2011-03-05"), "Ben Stiller", 45, "img1"));
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2011-03-14"), "Ben Stiller", 45, "img1"));
        new FilmsDao().saveFilm(new FilmsInfo(0, "Mon beau-père et nous", Time.valueOf("01:38:00"), "Cela fait dix ans", Date.valueOf("2011-06-14"), "Ben Stiller", 45, "img1"));

    }

    public String nbrFilmsDeuxMois() {
        int nb = 0;
        try {
            List<FilmsInfo> s = new FilmsDao().monthFilm();
            nb = s.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (nb == 3) ? "trois films!" : "" + nb + " films!";
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
        ConnexionBD.closeConnection();
    }
}
