
package Domein;

import java.sql.Date;
import java.util.List;


public class GevaarVeld extends Melding {

    private String categorieGevaar;
    private int gevaarNr;
    private double longitude, latitude;

    public GevaarVeld(String categorieGevaar, int gevaarNr, double longitude, double latitude, int persoonNr, int fotoNr, int teller, String straatNaam, String gemeente, String omschrijving, Date datum) {
        super(persoonNr, fotoNr, teller, straatNaam, gemeente, omschrijving, datum);
        this.categorieGevaar = categorieGevaar;
        this.gevaarNr = gevaarNr;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GevaarVeld() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getGevaarNr() {
        return gevaarNr;
    }

    public void setGevaarNr(int gevaarNr) {
        this.gevaarNr = gevaarNr;
    }

    public String getCategorie() {
        return categorieGevaar;
    }

    public void setCategorie(String categorie) {
        this.categorieGevaar = categorie;
    }
}
