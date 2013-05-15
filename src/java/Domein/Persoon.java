
package Domein;


public class Persoon {

    private int persoonNr;
    private String facebookAccount, naam, voornaam;

    public Persoon(int persoonNr, String facebookAccount, String naam, String voornaam) {
        this.persoonNr = persoonNr;
        this.facebookAccount = facebookAccount;
        this.naam = naam;
        this.voornaam = voornaam;
    }

    public Persoon() {
    }

    public int getPersoonNr() {
        return persoonNr;
    }

    public void setPersoonNr(int persoonNr) {
        this.persoonNr = persoonNr;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;

        }
        final Persoon other = (Persoon) obj;
        if (this.persoonNr != other.persoonNr) {
            return false;
        }
        return true;
    }
}
