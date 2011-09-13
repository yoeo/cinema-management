package org.cinema.dao.seance;

import java.sql.Date;
import java.sql.Time;

public class SeancesInfo {

    private int id;
    private int filmid;
    private int salleid;
    private Date debut;
    private Time heure;
    private boolean estTroisD;

    public SeancesInfo(int id, int filmid, int salleid, Date date, Time heure,
            boolean estTroisD) {
        super();
        this.id = id;
        this.filmid = filmid;
        this.salleid = salleid;
        this.debut = date;
        this.heure = heure;
        this.estTroisD = estTroisD;
    }
    /*  constructeur sans id*/

    public SeancesInfo(int filmid, int salleid, Date date, Time heure,
        boolean estTroisD) {
        super();
        this.filmid = filmid;
        this.salleid = salleid;
        this.debut = date;
        this.heure = heure;
        this.estTroisD = estTroisD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmid() {
        return filmid;
    }

    public void setFilmid(int filmid) {
        this.filmid = filmid;
    }

    public int getSalleid() {
        return salleid;
    }

    public void setSalleid(int salleid) {
        this.salleid = salleid;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public boolean isEstTroisD() {
        return estTroisD;
    }

    public void setEstTroisD(boolean estTroisD) {
        this.estTroisD = estTroisD;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public Time getHeure() {
        return heure;
    }
}
