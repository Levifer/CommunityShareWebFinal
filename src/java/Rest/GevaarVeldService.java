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
 


	//deze klasse is voor gegevens in de databank te steken, uit te halen, up te date en te verwijderen voor de table Inbox 
	@GET
        @Produces(MediaType.APPLICATION_JSON)
	public List<GevaarVeld> geefLijstGevaarVeld(String gemeente) {
		
		Statement statement;
		
		
		 try (Connection conn = source.getConnection()) {
                   statement = conn.createStatement();
			try (ResultSet rs = statement.executeQuery("SELECT GevaarNr, Omschrijving FROM gevaarveld WHERE Gemeente ='"+gemeente+"' Order by Datum decs ")) {
                            List<GevaarVeld> Gegevenslijst = new ArrayList<GevaarVeld>();
			while (rs.next()) 
			{
                                   String categorieGevaar=null;
                                   int persoonNr=0;
                                   int fotoNr=0;
                                    int teller=0;
                                    String straatNaam = null;
                                    String gemeent = null;
                                     Date datum= null;
				 
				GevaarVeld e = new GevaarVeld(categorieGevaar,
                                        rs.getInt("GevaarNr"),
                                        persoonNr,
                                        fotoNr,
                                        teller,
                                        straatNaam,
                                        gemeente,
                                        rs.getString("Omschrijving"),
                                        datum);
                                
				Gegevenslijst.add(e);	
                           }
                        statement.close();
                        return Gegevenslijst;
			
		} 
		
		 } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
           
                 
	}
         @Path("{id}")
         @DELETE            
	public void verwijderenVanEenGevaarVeld(@PathParam("id")int gevaarNr,int persoonNr) 
	{
              
		
			try(Connection conn = source.getConnection())
                        {
				String sql="DELETE FROM gevaarveld WHERE ("
                                        + "GevaarNr,"
                                        + "PersoonNr) "
                                        + "VALUES(?,?)";			
				PreparedStatement pstmt = conn.prepareStatement(sql);	
                                pstmt.setInt(1, gevaarNr);
				pstmt.executeUpdate();
				pstmt.close();										
							
			}catch(SQLException ex){
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
                                + "GevaarNr,"
                                + "PersoonNr,"
                                + "FotoNr,"
                                + "Teller,"
                                + "StraatNaam,"
                                + "gemeente,"
                                + "omschrijving,"
                                + "datum) "
                                + "VALUES(?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, gv.getCategorie());
                        pstmt.setInt(2, gv.getGevaarNr());
                        pstmt.setInt(3,gv.getPersoonNr());
                        pstmt.setInt(4,gv.getFotoNr());
                        pstmt.setInt(5,gv.getTeller());
                        pstmt.setString(6,gv.getStraatNaam());
                        pstmt.setString(7, gv.getGemeente());
                        pstmt.setString(8, gv.getOmschrijving());
                        pstmt.setDate(9, gv.getDatum());
                        
					
						
			pstmt.executeUpdate();
		}

		catch (SQLException ex) 
		{
			throw new WebApplicationException(ex);
		} 
	}    
          
          @GET
         @Consumes(MediaType.APPLICATION_JSON)
          
    public GevaarVeld zoekLijstGevaarVeld(int gevaarNr)
	{
		
		Statement statement;
		
		
		try(Connection conn = source.getConnection())
	    {
		statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM gevaarveld WHERE GevaarNr ='"+gevaarNr+"'");
	
		while(rs.next())
		{
			
			 gv = new GevaarVeld(rs.getString("CategorieGevaar"),
                         rs.getInt("GevaarNr"),
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
}
    

