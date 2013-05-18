/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Domein.GevaarVeld;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
@Path("gevaarveld")
public class GevaarVeldService {

    @Resource(name = "jdbc/communityshare")
    private DataSource source;
    private GevaarVeld gv;

    @GET
    @Path("{gemeente}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GevaarVeld> geefLijstGevaarVeld(@PathParam("gemeente") String gemeente) {

        try (Connection conn = source.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM gevaarveld WHERE Gemeente = ? Order by Datum DESC");
            pstmt.setString(1, gemeente);
            ResultSet rs = pstmt.executeQuery();
            List<GevaarVeld> Gegevenslijst = new ArrayList<>();


            while (rs.next()) {

                int fotoNr = 0;
                int teller = 0;



                GevaarVeld e = new GevaarVeld(rs.getString("CategorieGevaar"),
                        rs.getInt("GevaarNr"),
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
    @Path("gevaarveld/{persoonnr}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GevaarVeld> zoekLijst(@PathParam("persoonnr") int persoonnr) {


        try (Connection conn = source.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM gevaarveld WHERE PersoonNr = ? order by Datum DESC ");
            pstmt.setInt(1, persoonnr);
            ResultSet rs = pstmt.executeQuery();
            List<GevaarVeld> Gegevenslijst = new ArrayList<>();
            while (rs.next()) {

                gv = new GevaarVeld(rs.getString("CategorieGevaar"),
                        rs.getInt("GevaarNr"),
                        rs.getDouble("Longitude"),
                        rs.getDouble("Latitude"),
                        rs.getInt("PersoonNr"),
                        rs.getInt("FotoNr"),
                        rs.getInt("Teller"),
                        rs.getString("StraatNaam"),
                        rs.getString("Gemeente"),
                        rs.getString("Omschrijving"),
                        rs.getDate("Datum"));

                Gegevenslijst.add(gv);
            }
            return Gegevenslijst;

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);

        }

    }

    @Path("{gevaarNr},{persoonNr}")
    @DELETE
    public void verwijderenVanEenGevaar(@PathParam("gevaarNr") int gevaarNr, @PathParam("persoonNr") int persoonNr) {

        try (Connection conn = source.getConnection()) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM gevaarveld WHERE GevaarNr = ?");
            stat.setInt(1, gevaarNr);
            ResultSet rs = stat.executeQuery();
            if (!rs.next()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM gevaarveld WHERE GevaarNr= ? AND PersoonNr= ?")) {
                pstmt.setInt(1, gevaarNr);
                pstmt.setInt(2, persoonNr);
                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void aanmakenVanEenGevaarVeld(GevaarVeld gv) {
        int x = 0;
        try (Connection conn = source.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO gevaarveld("
                    + "GevaarNr,"
                    + "CategorieGevaar,"
                    + "PersoonNr,"
                    + "FotoNr,"
                    + "Teller,"
                    + "StraatNaam,"
                    + "Gemeente,"
                    + "Omschrijving,"
                    + "Datum,"
                    + "Longitude,"
                    + "Latitude) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, x);
            pstmt.setString(2, gv.getCategorie());
            pstmt.setInt(3, gv.getPersoonNr());
            pstmt.setInt(4, gv.getFotoNr());
            pstmt.setInt(5, gv.getTeller());
            pstmt.setString(6, gv.getStraatNaam());
            pstmt.setString(7, gv.getGemeente());
            pstmt.setString(8, gv.getOmschrijving());
            pstmt.setDate(9, gv.getDatum());
            pstmt.setDouble(10, gv.getLongitude());
            pstmt.setDouble(11, gv.getLatitude());


            pstmt.executeUpdate();

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @GET
    @Path("gevaarveld/{radius},{longitude},{latitude}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GevaarVeld> geefmarker(@PathParam("radius") int radius, @PathParam("longitude") double lon, @PathParam("latitude") double lat) {
        List<GevaarVeld> Gegevenslijst = new ArrayList<>();

        try (Connection conn = source.getConnection()) {
            double trek = radius / 112.00;
            double lonmax = lon + trek;
            double lonmin = lon - trek;
            double latmax = lat + trek;
            double latmin = lat - trek;

            PreparedStatement pst = conn.prepareStatement("SELECT * FROM gevaarveld WHERE Latitude BETWEEN ? AND ?");
            pst.setDouble(1, latmin);
            pst.setDouble(2, latmax);
            ResultSet rst = pst.executeQuery();
            if (!rst.next()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } else {
                rst.beforeFirst();
                while (rst.next()) {
                    int gevaarveld = rst.getInt("GevaarNr");
                    PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM gevaarveld WHERE GevaarNr = ? AND Longitude BETWEEN ? AND ?");
                    pstmt.setInt(1, gevaarveld);
                    pstmt.setDouble(2, lonmin);
                    pstmt.setDouble(3, lonmax);
                    ResultSet rs = pstmt.executeQuery();





                    while (rs.next()) {
                        int gevaarNr = 0;
                        int foto = 0;
                        int teller = 0;

                        gv = new GevaarVeld(rs.getString("CategorieGevaar"),
                                rs.getInt("GevaarNr"),
                                rs.getDouble("Longitude"),
                                rs.getDouble("Latitude"),
                                rs.getInt("PersoonNr"),
                                foto,
                                teller,
                                rs.getString("StraatNaam"),
                                rs.getString("Gemeente"),
                                rs.getString("Omschrijving"),
                                rs.getDate("Datum"));
                        Gegevenslijst.add(gv);

                    }


                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);

        }
        return Gegevenslijst;
    }
}
