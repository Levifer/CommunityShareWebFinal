
package Domein;


public class Like {

    private int eventNr, gevaarNr, persoonNr, likeNr;
    private boolean liken;

    public Like(int eventNr, int gevaarNr, int persoonNr, int likeNr, boolean liken) {
        this.eventNr = eventNr;
        this.gevaarNr = gevaarNr;
        this.persoonNr = persoonNr;
        this.likeNr = likeNr;
        this.liken = liken;
    }

    public Like() {
    }

    public int getLikeNr() {
        return likeNr;
    }

    public void setLikeNr(int likeNr) {
        this.likeNr = likeNr;
    }

    public int getEventNr() {
        return eventNr;
    }

    public void setEventNr(int eventNr) {
        this.eventNr = eventNr;
    }

    public int getGevaarNr() {
        return gevaarNr;
    }

    public void setGevaarNr(int gevaarNr) {
        this.gevaarNr = gevaarNr;
    }

    public int getPersoonNr() {
        return persoonNr;
    }

    public void setPersoonNr(int persoonNr) {
        this.persoonNr = persoonNr;
    }

    public boolean isLiken() {
        return liken;
    }

    public void setLiken(boolean liken) {
        this.liken = liken;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;

        }
        final Like other = (Like) obj;
        if (this.likeNr != other.likeNr) {
            return false;
        }
        return true;
    }
}
