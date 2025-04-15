package org.example;

import java.util.Random;

public class ZufallsGefaessFabrik implements Runnable, Gefaessfabrik {

    @Override
    public Gefaess erstelleGefaess(double preisInhalt, double seitenlaenge, double hoehe) {

//        while (true) {
//
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            Random r = new Random();

            int zufall = r.nextInt(3);

            Gefaessfabrik f = null;

            if (zufall == 0) {
                f = new ZylinderFabrik();
                return f.erstelleGefaess(preisInhalt, seitenlaenge, hoehe);
            } else if (zufall == 1) {
                f = new QuaderFabrik();
                return f.erstelleGefaess(preisInhalt, seitenlaenge, hoehe);
            } else {
                f = new PyramidenFabrik();
                return f.erstelleGefaess(hoehe, seitenlaenge, preisInhalt);
            }
       // }
    }

    @Override
    public void run() {

    }
}
