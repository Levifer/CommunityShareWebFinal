package Rest;

import Domein.Event;
import java.net.URI;
import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("event")
public class EventService {

    @Resource(name = "jdbc/communityshare")
    private DataSource source;
    private Event e;

    @GET
    @Path("{gemeente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> geefLijstEvent(@PathParam("gemeente") String gemeente) {

        try (Connection conn = source.getConnection()) {

            try (PreparedStatement pst = conn.prepareStatement("delete from event where datum < curdate()")) {
                pst.executeUpdate();
            }

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM event WHERE Gemeente = ? Order by Datum DESC");
            pstmt.setString(1, gemeente);
            ResultSet rs = pstmt.executeQuery();
            List<Event> Gegevenslijst = new ArrayList<>();



            while (rs.next()) {

                int fotoNr = 0;
                int teller = 0;



                Event e = new Event(rs.getString("CategorieEvent"),
                        rs.getInt("EventNr"),
                        rs.getDouble("Longitude"),
                        rs.getDouble("Latitude"),
                        rs.getInt("PersoonNr"),
                        fotoNr,
                        teller,
                        rs.getString("StraatNaam"),
                        rs.getString("Gemeente"),
                        rs.getString("Omschrijving"),
                        rs.getDate("Datum"));

                Gegevenslijst.add(e);

            }
            return Gegevenslijst;

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }

    }

    @GET
    @Path("event/{persoonsnr}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> GeeflijstVanPeroon(@PathParam("persoonsnr") int persoonnr) {


        try (Connection conn = source.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM event WHERE PersoonNr = ? Order by Datum DESC ");
            pstmt.setInt(1, persoonnr);
            ResultSet rs = pstmt.executeQuery();
            List<Event> Gegevenslijst = new ArrayList<>();
            while (rs.next()) {

                e = new Event(rs.getString("CategorieEvent"),
                        rs.getInt("EventNr"),
                        rs.getDouble("Longitude"),
                        rs.getDouble("Latitude"),
                        rs.getInt("PersoonNr"),
                        rs.getInt("FotoNr"),
                        rs.getInt("Teller"),
                        rs.getString("StraatNaam"),
                        rs.getString("Gemeente"),
                        rs.getString("Omschrijving"),
                        rs.getDate("Datum"));

                Gegevenslijst.add(e);

            }

            return Gegevenslijst;

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);

        }

    }

    @Path("{eventNr},{persoonNr}")
    @DELETE
    public void verwijderenVanEenEvent(@PathParam("eventNr") int eventNr, @PathParam("persoonNr") int persoonNr) {

        try (Connection conn = source.getConnection()) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM event WHERE EventNr = ?");
            stat.setInt(1, eventNr);
            ResultSet rs = stat.executeQuery();
            if (!rs.next()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM event WHERE EventNr= ? AND PersoonNr= ?")) {
                pstmt.setInt(1, eventNr);
                pstmt.setInt(2, persoonNr);
                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void aanmakenVanEenEvent(Event e) {


        int x = 0;
        try (Connection conn = source.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO event("
                    + "EventNr,"
                    + "CategorieEvent,"
                    + "PersoonNr,"
                    + "FotoNr,"
                    + "Teller,"
                    + "StraatNaam,"
                    + "Gemeente,"
                    + "Omschrijving,"
                    + "Datum,"
                    + "Longitude,"
                    + "Latitude)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, x);
            pstmt.setString(2, e.getCategorie());
            pstmt.setInt(3, e.getPersoonNr());
            pstmt.setInt(4, e.getFotoNr());
            pstmt.setInt(5, e.getTeller());
            pstmt.setString(6, e.getStraatNaam());
            pstmt.setString(7, e.getGemeente());
            pstmt.setString(8, e.getOmschrijving());
            pstmt.setDate(9, e.getDatum());
            pstmt.setDouble(10, e.getLongitude());
            pstmt.setDouble(11, e.getLatitude());



            pstmt.executeUpdate();
            //return Response.created(URI.create("/" + e.getOmschrijving())).build();
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @GET
    @Path("event/{radius},{longitude},{latitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> geefmarker(@PathParam("radius") int radius, @PathParam("longitude") double lon, @PathParam("latitude") double lat) {
        List<Event> Gegevenslijst = new ArrayList<>();

        try (Connection conn = source.getConnection()) {
            double trek = radius / 112.00;
            double lonmax = lon + trek;
            double lonmin = lon - trek;
            double latmax = lat + trek;
            double latmin = lat - trek;






            PreparedStatement pst = conn.prepareStatement("SELECT * FROM event WHERE Latitude BETWEEN ? AND ?");
            pst.setDouble(1, latmin);
            pst.setDouble(2, latmax);
            ResultSet rst = pst.executeQuery();
            if (!rst.next()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } else {
                rst.beforeFirst();
                while (rst.next()) {
                    int eventnr = rst.getInt("EventNr");
                    PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM event WHERE EventNr = ? AND Longitude BETWEEN ? AND ?");
                    pstmt.setInt(1, eventnr);
                    pstmt.setDouble(2, lonmin);
                    pstmt.setDouble(3, lonmax);
                    ResultSet rs = pstmt.executeQuery();





                    while (rs.next()) {

                        int foto = 0;
                        int teller = 0;

                        e = new Event(rst.getString("CategorieEvent"),
                                rs.getInt("EventNr"),
                                rs.getDouble("Longitude"),
                                rs.getDouble("Latitude"),
                                rs.getInt("PersoonNr"),
                                foto,
                                teller,
                                rs.getString("StraatNaam"),
                                rs.getString("Gemeente"),
                                rs.getString("Omschrijving"),
                                rs.getDate("Datum"));
                        Gegevenslijst.add(e);

                    }
                }



            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);

        }
        return Gegevenslijst;
    }
}
