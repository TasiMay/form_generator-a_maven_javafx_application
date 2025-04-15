package org.example;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Executable;

class HausTausenderWunderTest {

    WunderController mockedWunderController = mock(WunderController.class);

    @Test
    void getGesamtPreis() {
        HausTausenderWunder htw = new HausTausenderWunder();
        htw.anmelden(mockedWunderController);
        Gefaess gefaess = new Zylinder(0.1, 1, 1);
        htw.gefaessEinfuegen(gefaess);
        verify(mockedWunderController).propertyChange(any(PropertyChangeEvent.class));
    }
}