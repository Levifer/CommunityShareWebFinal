/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;


import Domein.GevaarVeld;
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

/**
 *
 * @author arne
 */

@Stateless
@Path("gevaarveld")
public class GevaarVeldService
{
       @Resource(name = "jdbc/communityshare")
        private DataSource source;
        private GevaarVeld gv;
 


	
  @GET
  @Path("{gemeente}")
  @Produces(MediaType.APPLICATION_JSON)
	public List<GevaarVeld> geefLijstGevaarVeld(@PathParam("gemeente")String gemeente)  
	{

            try (Connection conn = source.getConnection())
            {
              PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM gevaarveld WHERE Gemeente = ? Order by Datum"); 
              pstmt.setString(1, gemeente);
                ResultSet rs = pstmt.executeQuery(); 
                List<GevaarVeld> Gegevenslijst = new ArrayList<>();
       
                    
                                while (rs.next()) 
			{
                                   String categorieEvent=null;
                                   int fotoNr=0;
                                   int teller=0;
                                   String straatNaam = null;
                                   Date datum= null;
                                  
                                    
				GevaarVeld e = new GevaarVeld(categorieEvent,
                                        rs.getInt("GevaarNr"),
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
 @Path("gevaarveld/{gevaarNr}")
 @Produces(MediaType.APPLICATION_JSON)
    public GevaarVeld zoekLijst(@PathParam("gevaarNr")int gevaarVeld) 
	{
		

		try( Connection conn = source.getConnection())
	    {
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM gevaarveld WHERE GevaarNr = ? ");
                pstmt.setInt(1, gevaarVeld);
		ResultSet rs = pstmt.executeQuery();
              	
		while(rs.next())
		{

			 gv = new GevaarVeld(rs.getString("CategorieGevaar"),
                         gevaarVeld,
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
		return gv;
	}
   @Path("{gevaarNr},{persoonNr}")
  @DELETE
	public void verwijderenVanEenGevaar(@PathParam("gevaarNr")int gevaarnr,@PathParam("persoonNr")int persoonNr) 
	{

			try(Connection conn = source.getConnection())
                        {
                                PreparedStatement stat = conn.prepareStatement("SELECT * FROM gevaarveld WHERE GevaarNr = ?");
                                stat.setInt(1, gevaarnr);
                                ResultSet rs = stat.executeQuery();
                                     if (!rs.next()) 
                                     {
                                         throw new WebApplicationException(Response.Status.NOT_FOUND);
                                     }
          		
                                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM gevaarveld WHERE GevaarNr= ? AND PersoonNr= ?")) 
                                {
                                    pstmt.setInt(1, gevaarnr);
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
	public void aanmakenVanEenGevaarVeld(GevaarVeld gv) 
	{
                
                        try(Connection conn = source.getConnection())
                        {
                        // String categorieEvent, int meldingNr, int persoonNr, int fotoNr, int teller, String straatNaam, String gemeente, String omschrijving, Date datum		
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO gevaarveld("
                                + "CategorieGevaar,"
                                + "PersoonNr,"
                                + "FotoNr,"
                                + "Teller,"
                                + "StraatNaam,"
                                + "Gemeente,"
                                + "Omschrijving,"
                                + "Datum) "
                                + "VALUES(?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, gv.getCategorie());
                        pstmt.setInt(2,gv.getPersoonNr());
                        pstmt.setInt(3,gv.getFotoNr());
                        pstmt.setInt(4,gv.getTeller());
                        pstmt.setString(5,gv.getStraatNaam());
                        pstmt.setString(6, gv.getGemeente());
                        pstmt.setString(7, gv.getOmschrijving());
                        pstmt.setDate(8, gv.getDatum());
                        
					
						
			pstmt.executeUpdate();
		}

		catch (SQLException ex) 
		{
			throw new WebApplicationException(ex);
		} 
	}    
          
 
}
    

