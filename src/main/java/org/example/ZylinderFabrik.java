package org.example;

public class ZylinderFabrik implements Gefaessfabrik {
    @Override
    public Gefaess erstelleGefaess(double preisInhalt, double seitenlaenge, double hoehe) {
        return new Zylinder(preisInhalt, seitenlaenge, hoehe);
    }
}
