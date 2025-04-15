package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

public class WunderController implements PropertyChangeListener {

    @FXML
    private TextArea txtGefaesse;
    @FXML
    private TextField txtPreisinhalt;
    @FXML
    private TextField txtLaenge;
    @FXML
    private TextField txtHoehe;
    @FXML
    private ChoiceBox<String> chbForm;
    @FXML
    private Label lblMeldung;
    @FXML
    private TextField txtGesamtpreis;

    private HausTausenderWunder htw = new HausTausenderWunder();

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    @FXML
    private void initialize() {
        ObservableList<String> obs;
        obs = FXCollections.observableArrayList(new String[]{"Zylinder", "Quader", "Pyramide"});
        chbForm.setItems(obs);
        chbForm.getSelectionModel().selectFirst();
        txtGesamtpreis.textProperty().bind(htw.getGesamtPreis().asString());
        htw.anmelden(this);
        System.out.println("initialize");
    }

    private void listeAktualisieren(String text) {
        Platform.runLater(() -> {
            txtGefaesse.setText(text);
        });
    }

    //*
//	 * startet die Produktion
    public void starten() {
        double preisInhalt;
        double laenge;
        double hoehe;
        try {
            preisInhalt = Double.parseDouble(txtPreisinhalt.getText());
            laenge = Double.parseDouble(txtLaenge.getText());
            hoehe = Double.parseDouble(txtHoehe.getText());
            ZufallsGefaessFabrik fabrik = new ZufallsGefaessFabrik();
            htw.produktionStarten(fabrik, preisInhalt, laenge, hoehe);
            lblMeldung.setText("Produktion läuft");
        } catch (NumberFormatException e) {
            lblMeldung.setText("Keine Zahl!");
        }
    }


    public void stoppen() {
        htw.produktionStoppen();
        lblMeldung.setText("Produktion gestoppt");
    }

//*
//	 * kauft ein Gefäß

    public void kaufen() {
        try {
            double l = Double.parseDouble(txtLaenge.getText());
            double h = Double.parseDouble(txtHoehe.getText());
            double p = Double.parseDouble(txtPreisinhalt.getText());

            String form = chbForm.getValue();
            Gefaess g = null;
            switch (form) {
                case "Zylinder":
                    g = new Zylinder(p, l, h);
                    break;
                case "Quader":
                    g = new Quader(p, l, h);
                    break;
                case "Pyramide":
                    g = new Pyramide(p, l, h);
                    break;
            }
            htw.gefaessKaufen(g);
        } catch (NichtVorhandenException e) {
            lblMeldung.setText("Gefaess nicht vorhanden!");
            return;
        } catch (NumberFormatException e) {
            lblMeldung.setText("Keine Zahl");
            return;
        }
        lblMeldung.setText("");
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("propertyChange");

        listeAktualisieren(htw.getGefaessliste());
    }
}
