package Rest;

import Domein.Persoon;
import java.net.URI;
import java.sql.Connection;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Stateless
@Path("persoon")
public class PersoonService {

    @Resource(name = "jdbc/communityshare")
    private DataSource source;
    private Persoon Pers;

    @Path("persoon/{persoon}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String geefpersoon(@PathParam("persoon") int pers) {


        try (Connection conn = source.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT Voornaam, Naam FROM persoon WHERE PersoonNr = ?");
            pstmt.setInt(1, pers);
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            String naam = rs.getString("Naam");
            String voornaam = rs.getString("Voornaam");
            String volnaam = naam + " " + voornaam;

            return volnaam;

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void voegEenPersoonToe(Persoon Pers) {


        try (Connection conn = source.getConnection()) {

            int x = 0;

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO persoon ("
                    + "PersoonNr,"
                    + "FacebookAccount,"
                    + "Voornaam,"
                    + "Naam)"
                    + "VALUES(?,?,?,?)");
            pstmt.setInt(1, x);
            pstmt.setString(2, Pers.getFacebookAccount());
            pstmt.setString(3, Pers.getVoornaam());
            pstmt.setString(4, Pers.getNaam());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }

    }

    @Path("{accountFacebook}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public int logInFacebook(@PathParam("accountFacebook") String facebookAccount) {
        int persoonnr = 0;
        try (Connection c = source.getConnection()) {
            //
            PreparedStatement pstmt = c.prepareStatement("SELECT * From persoon WHERE FacebookAccount = ?");
            pstmt.setString(1, facebookAccount);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } else {
                persoonnr = rs.getInt("PersoonNr");
                return persoonnr;
            }



        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }


    }
}
