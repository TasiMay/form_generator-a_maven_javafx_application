package org.example;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * stellt ein Gefäß für den Verkauf dar
 * @author Doro
 *
 */
public abstract class Gefaess{
	/**
	 * Preis pro cm³ des Inhalts des Gefäßes
	 */
	private double preisInhalt;

	/**
	 * erzeugt ein Gefäß mit dem angegebenen Inhaltspreis
	 * @param preisInhalt
	 */
	public Gefaess(double preisInhalt) {
		this.preisInhalt = preisInhalt;
	}

	/**
	 * liefert den Namen des Gefäßes zurück
	 * @return Name des Gefäßes
	 */
	public String getName()
	{
		return this.getClass().getSimpleName()+ "(" + this.preisInhalt +")";
	}
	
	/**
	 * Vergleich von this mit other; Zwei Gefäße gelten als gleich,
	 * wenn sie vom gleichen Subtyp sind und den gleichen Preis haben
	 * @param other
	 * @return true, wenn beide Gefäße als gleich gelten
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		Gefaess g = (Gefaess) other;
		if(this.getPreis() == g.getPreis())
			return true;
		else
			return false;
	}

	public final double getPreis() {
		//Volumen * Preis pro cm³
		double preis = volumenBerechnen() * preisInhalt;

		return preis;
	}

	public abstract double volumenBerechnen();
	
	@Override
	public int hashCode()
	{
		return this.getClass().hashCode() * 59 + Double.hashCode(this.getPreis());
	}
	
	@Override
	public String toString()
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return this.getClass().getSimpleName() + " zum Preis von "
				+ nf.format(this.getPreis());
	}
}
