package Rest;

import Domein.Admin;
import Domein.Persoon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("admin")
public class AdminService {

    @Resource(name = "jdbc/communityshare")
    private DataSource source;
    private Admin a;

    @GET
    @Path("{gebruiker},{wacht}")
    @Produces(MediaType.APPLICATION_JSON)
    public Admin login(@PathParam("gebruiker") String gebruik, @PathParam("wacht") String wacht) {

        try (Connection conn = source.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM admin WHERE Gebruikersnaam = ? AND Wachtwoord = ?");
            pstmt.setString(1, gebruik);
            pstmt.setString(2, wacht);
            ResultSet rs = pstmt.executeQuery();
            rs.next();






            Admin e = new Admin(rs.getString("Gebruikersnaam"),
                    rs.getString("Gemeente"),
                    rs.getString("Wachtwoord"));


            return e;
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }

    }

    @Path("{eventNr}")
    @DELETE
    public void verwijderenVanEenEvent(@PathParam("eventNr") int eventNr) {

        try (Connection conn = source.getConnection()) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM event WHERE EventNr = ?");
            stat.setInt(1, eventNr);
            ResultSet rs = stat.executeQuery();
            if (!rs.next()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM event WHERE EventNr= ? ")) {
                pstmt.setInt(1, eventNr);

                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Path("admin/{gevaarNr}")
    @DELETE
    public void verwijderenVanEensituatie(@PathParam("gevaarNr") int gevaarnr) {

        try (Connection conn = source.getConnection()) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM gevaarveld WHERE GevaarNr = ?");
            stat.setInt(1, gevaarnr);
            ResultSet rs = stat.executeQuery();
            if (!rs.next()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM gevaarveld WHERE GevaarNr= ? ")) {
                pstmt.setInt(1, gevaarnr);

                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
