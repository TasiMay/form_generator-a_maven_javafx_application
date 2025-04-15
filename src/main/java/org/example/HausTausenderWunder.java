package org.example;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.synchronizedMap;

/**
 * Diese Klasse stellt den Laden "Haus Tausender Wunder" dar, der
 * viele tolle Gefäße produziert und aus dem Lager heraus verkauft
 *
 * @author Doro
 */
public class HausTausenderWunder {

    private Map<Gefaess, Integer> lagerverwaltung = synchronizedMap(new HashMap<>());

    private DoubleProperty gesamtPreis;

    private PropertyChangeSupport support;
    ScheduledExecutorService ses;
    private ScheduledFuture<?> scheduleFuture;

    public HausTausenderWunder() {
        ses = Executors.newSingleThreadScheduledExecutor();
        gesamtPreis = new SimpleDoubleProperty();
        support = new PropertyChangeSupport(this);
    }

    private void setGesamtPreis() {
        double i = 0.0;
        for (Gefaess g : lagerverwaltung.keySet()) {
            i += g.getPreis() * lagerverwaltung.get(g);
        }

        gesamtPreis.set(i);
    }

    public DoubleProperty getGesamtPreis() {
        return gesamtPreis;
    }

    public String getGefaessliste() {
        StringBuilder sb = new StringBuilder();
        for (Gefaess g : lagerverwaltung.keySet()) {
            sb.append(g).append(" ").append(lagerverwaltung.get(g)).append("\n");
        }
        return sb.toString();
    }

    /**
     * startet die Endlosproduktion von Gefäßen der angegebenen Größe
     * mit Hilfe der Fabrik und fügt die erstellten Gefäße in die
     * Lagerverwaltung ein
     *
     * @param fabrik
     * @param preisInhalt
     * @param laenge
     * @param hoehe
     */
    public void produktionStarten(ZufallsGefaessFabrik fabrik, double preisInhalt, double laenge, double hoehe) {
        scheduleFuture = ses.scheduleAtFixedRate(() -> {
            Gefaess g = fabrik.erstelleGefaess(preisInhalt, laenge, hoehe);
            gefaessEinfuegen(g);
            setGesamtPreis();
            System.out.println("Gesamtpreis: " + gesamtPreis);
        }, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * stoppt alle laufenden Produktionen
     */
    public void produktionStoppen() {
        scheduleFuture.cancel(true);
    }

    /**
     * entnimmt das Gefäß g aus der Lagerverwaltung
     *
     * @param g
     * @throws NichtVorhandenException, wenn g nicht im Lager vorhanden ist
     */

    public void gefaessKaufen(Gefaess g) throws NichtVorhandenException {
        Map<Gefaess, Integer> alt = synchronizedMap(new HashMap<>(lagerverwaltung));
        if (lagerverwaltung.containsKey(g)) {
            int anzahl = lagerverwaltung.get(g);
            if (anzahl > 0) {
                lagerverwaltung.put(g, anzahl - 1);
                benachrichtigen(alt);
                setGesamtPreis();
            }
            if (anzahl == 1) {
                lagerverwaltung.remove(g);
                benachrichtigen(alt);
                setGesamtPreis();
            }
        } else {
            throw new NichtVorhandenException();
        }
    }


    /**
     * fügt das Gefäß g in die Lagerverwaltung ein
     *
     * @param g
     */
    protected void gefaessEinfuegen(Gefaess g) {
        if (g != null) {
            Map<Gefaess, Integer> alt = synchronizedMap(new HashMap<>());
            if (lagerverwaltung.containsKey(g)) {
                lagerverwaltung.put(g, lagerverwaltung.get(g) + 1);
                benachrichtigen(alt);
            } else {
                lagerverwaltung.put(g, 1);
                benachrichtigen(alt);
            }
        }
    }

    /**
     * liefert einen textuelle Liste aller Gefäße mit ihrer Anzahl zurück
     *
     * @return
     */

    public void anmelden(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void abmelden(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void benachrichtigen(Map<Gefaess, Integer> lagerAlt) {

        support.firePropertyChange("lagerverwaltung aktuell", lagerAlt, this.lagerverwaltung);
    }

}
