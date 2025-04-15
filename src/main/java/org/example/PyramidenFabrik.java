package org.example;

public class PyramidenFabrik implements Gefaessfabrik {
    @Override
    public Gefaess erstelleGefaess(double preisInhalt, double seitenlaenge, double hoehe) {
        return new Pyramide(hoehe, seitenlaenge, preisInhalt);
    }
}
