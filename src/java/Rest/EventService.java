
package Rest;

import Domein.Event;
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

@Stateless
@Path("event")
public class EventService 
{
    @Resource(name = "jdbc/communityshare")
    private DataSource source;
    private Event e;
 


	//deze klasse is voor gegevens in de databank te steken, uit te halen, up te date en te verwijderen voor de table Inbox 
	@GET
        @Produces(MediaType.APPLICATION_JSON)
	public List<Event> geefLijstEvent(String gemeente) throws SQLException 
	{

            try (Connection conn = source.getConnection()) {
		            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM TBL_MESSAGE INNER JOIN TBL_USER ON TBL_MESSAGE.AUTHOR = TBL_USER.ID")) {
                            try (ResultSet rs = stat.executeQuery()) {
                                List<Event> Gegevenslijst = new ArrayList<Event>();
                                
                                while (rs.next()) 
			{
                                   String categorieEvent=null;
                                   int persoonNr=0;
                                   int fotoNr=0;
                                    int teller=0;
                                    String straatNaam = null;
                                    String gemeent = null;
                                     Date datum= null;
				 
				Event e = new Event(categorieEvent,
                                        rs.getInt("EventNr"),
                                        persoonNr,
                                        fotoNr,
                                        teller,
                                        straatNaam,
                                        gemeente,
                                        rs.getString("Omschrijving"),
                                        datum);
                                
				Gegevenslijst.add(e);	
	       }
                                 return Gegevenslijst;
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
                           
            }
        }
        
         @Path("{id}")
         @DELETE
	public void verwijderenVanEenEvent(@PathParam("id")int eventNr,int persoonNr) 
	{

			try(Connection conn = source.getConnection()){
                        {
				String sql="DELETE FROM event WHERE ("
                                        + "EventNr,"
                                        + "PersoonNr) "
                                        + "VALUES(?,?)";			
                                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                    pstmt.setInt(1, eventNr);
                                    pstmt.executeUpdate();
                                }										
                        }
			  } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
      } 
         
         @POST
         @Consumes(MediaType.APPLICATION_JSON)
	public void aanmakenVanEenEvent(Event e) throws SQLException 
	{
               

                
                        try( Connection conn = source.getConnection())
                        {
                        // String categorieEvent, int meldingNr, int persoonNr, int fotoNr, int teller, String straatNaam, String gemeente, String omschrijving, Date datum		
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO event("
                                
                                + "PersoonNr,"
                                + "FotoNr,"
                                + "Teller,"
                                + "StraatNaam,"
                                + "Gemeente,"
                                + "Datum,"
                                + "CategorieEvent,"
                                + "Omschrijving)"
                                + "VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setString(7, e.getCategorie());
                        pstmt.setInt(1,e.getPersoonNr());
                        pstmt.setInt(2,e.getFotoNr());
                        pstmt.setInt(3,e.getTeller());
                        pstmt.setString(4,e.getStraatNaam());
                        pstmt.setString(5, e.getGemeente());
                        pstmt.setString(8, e.getOmschrijving());
                        pstmt.setDate(6, e.getDatum());
                        
					
						
			pstmt.executeUpdate();
			
		}
                        
		catch (SQLException ex) 
		{
			throw new WebApplicationException(ex);
		} 
	}    
         
         @GET
         @Consumes(MediaType.APPLICATION_JSON)
    public Event zoekLijst(int eventNr) throws SQLException
	{
		Statement statement;

		try( Connection conn = source.getConnection())
	    {
		statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM event WHERE EventNr ='"+eventNr+"'");
	
		while(rs.next())
		{

			 e = new Event(rs.getString("CategorieEvent"),
                         rs.getInt("EventNr"),
                         rs.getInt("PersoonNr"),
                         rs.getInt("FotoNr"),
                         rs.getInt("Teller"),
                         rs.getString("StraatNaam"),
                         rs.getString("Gemeente"),
                         rs.getString("Omschrijving"),
                         rs.getDate("Datum"));
			 
			
		}
		

	    }
		catch(SQLException ex)
		{
			 throw new WebApplicationException(ex);
			
		} 
		return e;
	}
}
