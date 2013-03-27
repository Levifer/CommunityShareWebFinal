
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
import javax.ws.rs.core.Response;

@Stateless
@Path("event")
public class EventService 
{
    @Resource(name = "jdbc/communityshare")
    private DataSource source;
    private Event e;
   


  @GET
  @Path("{gemeente}")
  @Produces(MediaType.APPLICATION_JSON)
	public List<Event> geefLijstEvent(@PathParam("gemeente")String gemeente)  
	{

            try (Connection conn = source.getConnection())
            {
              PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM event WHERE Gemeente = ? Order by Datum"); 
              pstmt.setString(1, gemeente);
                ResultSet rs = pstmt.executeQuery(); 
                List<Event> Gegevenslijst = new ArrayList<>();
       
                    
                                while (rs.next()) 
			{
                                   String categorieEvent=null;
                                   int fotoNr=0;
                                   int teller=0;
                                   String straatNaam = null;
                                   Date datum= null;
                                  
                                    
				Event e = new Event(categorieEvent,
                                        rs.getInt("EventNr"),
                                        rs.getInt("PersoonNr"),
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
         catch (SQLException ex) 
         {
            throw new WebApplicationException(ex);
        }
                           
  }
  
 @GET
 @Path("event/{eventNr2}")
 @Produces(MediaType.APPLICATION_JSON)
    public Event zoekLijst(@PathParam("eventNr2")int eventNr) 
	{
		

		try( Connection conn = source.getConnection())
	    {
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM event WHERE EventNr = ? ");
                pstmt.setInt(1, eventNr);
		ResultSet rs = pstmt.executeQuery();
              	
		while(rs.next())
		{

			 e = new Event(rs.getString("CategorieEvent"),
                         eventNr,
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
        
  @Path("{eventNr},{persoonNr}")
  @DELETE
	public void verwijderenVanEenEvent(@PathParam("eventNr")int eventNr,@PathParam("persoonNr")int persoonNr) 
	{

			try(Connection conn = source.getConnection())
                        {
                                PreparedStatement stat = conn.prepareStatement("SELECT * FROM event WHERE EventNr = ?");
                                stat.setInt(1, eventNr);
                                ResultSet rs = stat.executeQuery();
                                     if (!rs.next()) 
                                     {
                                         throw new WebApplicationException(Response.Status.NOT_FOUND);
                                     }
          		
                                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM event WHERE EventNr= ? AND PersoonNr= ?")) 
                                {
                                    pstmt.setInt(1, eventNr);
                                    pstmt.setInt(2, persoonNr);
                                    pstmt.executeUpdate();
                                }										
                        
			  }
                        catch (SQLException ex) 
                          {
                            throw new WebApplicationException(ex);
                          }
      } 
         
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
	public void aanmakenVanEenEvent(Event e) 
	{
               

                
                        try( Connection conn = source.getConnection())
                        {
                        // String categorieEvent, int meldingNr, int persoonNr, int fotoNr, int teller, String straatNaam, String gemeente, String omschrijving, Date datum		
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO event("
                                + "CategorieEvent,"
                                + "PersoonNr,"
                                + "FotoNr,"
                                + "Teller,"
                                + "StraatNaam,"
                                + "Gemeente,"
                                + "Omschrijving)"
                                + "Datum,"
                                + "VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, e.getCategorie());
                        pstmt.setInt(2,e.getPersoonNr());
                        pstmt.setInt(3,e.getFotoNr());
                        pstmt.setInt(4,e.getTeller());
                        pstmt.setString(5,e.getStraatNaam());
                        pstmt.setString(6, e.getGemeente());
                        pstmt.setString(7, e.getOmschrijving());
                        pstmt.setDate(8, e.getDatum());
                        
					
						
			pstmt.executeUpdate();
			
		}
                        
		catch (SQLException ex) 
		{
			throw new WebApplicationException(ex);
		} 
	}    
         
 
}
