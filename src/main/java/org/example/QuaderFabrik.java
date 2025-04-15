package org.example;

public class QuaderFabrik implements Gefaessfabrik {
    @Override
    public Gefaess erstelleGefaess(double preisInhalt, double seitenlaenge, double hoehe) {
        return new Quader(preisInhalt, seitenlaenge, hoehe);
    }
}
