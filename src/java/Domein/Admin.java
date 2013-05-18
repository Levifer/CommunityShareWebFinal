package Domein;

public class Admin {

    private String gebruikersnaam, gemeente, wachtwoord;

    public Admin() {
    }

    public Admin(String gebruikersnaam, String gemeente, String wachtwoord) {
        this.gebruikersnaam = gebruikersnaam;
        this.gemeente = gemeente;
        this.wachtwoord = wachtwoord;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}