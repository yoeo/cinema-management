/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carteAbonne;

import java.sql.Date;
import java.sql.Time;
import org.concordion.integration.junit3.ConcordionTestCase;
import org.cinema.model.ConnexionBD;
import java.util.List;
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

/**
 *
 * @author med
 */
public class ReservationTest extends ConcordionTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ConnexionBD.loadTables("mem:Cinema", null);
        new FilmsDao().saveFilm(new FilmsInfo(0,"Mon beau-père et nous", Time.valueOf("01:38:00"),"Cela fait dix ans", Date.valueOf("2011-02-02"),"Ben Stiller", 45, "img1"));
        new SallesDao().saveSalle(new SallesInfo(0,"Carlie S1", 104, true));
        new SeancesDao().saveSeance(new SeancesInfo(0, 1, 1, Date.valueOf("2011-02-14"), Time.valueOf("02:30:33"), true));          
        new CarteAbonnesDao().saveCarteAbonne(new CarteAbonnesInfo(0, Date.valueOf("2011-04-11"), "DODO", "BA", 25, "mymail@live.com"));
        new TypeCartesDao().saveTypeCarte(new TypeCartesInfo(0, "famille", 7));
        new ReservationsDao().saveReservation(new ReservationsInfo(0, 1, 1, 1));
        new ReservationsDao().saveReservation(new ReservationsInfo(0, 1, 1, 1));
        new ReservationsDao().saveReservation(new ReservationsInfo(0, 1, 1, 1));
        new ReservationsDao().saveReservation(new ReservationsInfo(0, 1, 1, 1));
        
    }

    public String reservationPleinTarif() {
        int nb = 0;
        try {
            List<ReservationsInfo> s = new ReservationsDao().allReservation();
            nb = s.size();
        } catch (Exception e) {            
            e.printStackTrace();
        }
        return (nb == 4) ? "quatre reservations!" : "" + nb + " reservations!";
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
        ConnexionBD.closeConnection();
    }
}
