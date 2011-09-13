/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cinema.dao.reservation;


/**
 * 
 * @author lynda
 */
public class ReservationsInfo
{

	private int id;
	private int carteAbonneId;
	private int typeCarteId;
	private int seanceId;
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getCarteAbonneId()
	{
		return carteAbonneId;
	}

	public void setCarteAbonneId(int carteAbonneId)
	{
		this.carteAbonneId = carteAbonneId;
	}

	public int getTypeCarteId()
	{
		return typeCarteId;
	}

	public void setTypeCarteId(int typeCarteId)
	{
		this.typeCarteId = typeCarteId;
	}

	public int getSeanceId()
	{
		return seanceId;
	}

	public void setSeanceId(int seanceId)
	{
		this.seanceId = seanceId;
	}
/*  constructeur avec id */
	public ReservationsInfo(int id, int carteAbonneId, int typeCarteId,
			int seanceId)
	{
		super();
		this.id = id;
		this.carteAbonneId = carteAbonneId;
		this.typeCarteId = typeCarteId;
		this.seanceId = seanceId;
	}
 /*  constructeur sans id */
        public ReservationsInfo(int carteAbonneId, int typeCarteId,
			int seanceId)
	{
		super();
	
		this.carteAbonneId = carteAbonneId;
		this.typeCarteId = typeCarteId;
		this.seanceId = seanceId;
	}
}
