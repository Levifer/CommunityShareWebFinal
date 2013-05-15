package Domein;

import java.sql.Date;

public class Event extends Melding {

    private String categorieEvent;
    private int eventNr;
    private double longitude, latitude;

    public Event(String categorieEvent, int eventNr, double longitude, double latitude, int persoonNr, int fotoNr, int teller, String straatNaam, String gemeente, String omschrijving, Date datum) {
        super(persoonNr, fotoNr, teller, straatNaam, gemeente, omschrijving, datum);
        this.categorieEvent = categorieEvent;
        this.eventNr = eventNr;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Event() {
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

    public int getEventNr() {
        return eventNr;
    }

    public void setEventNr(int eventNr) {
        this.eventNr = eventNr;
    }

    public String getCategorie() {
        return categorieEvent;
    }

    public void setCategorie(String categorie) {
        this.categorieEvent = categorie;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;

        }
        final Event other = (Event) obj;
        if (this.eventNr != other.eventNr) {
            return false;
        }
        return true;
    }
}
