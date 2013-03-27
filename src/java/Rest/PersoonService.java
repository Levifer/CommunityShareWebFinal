/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Domein.Persoon;
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

/**
 *
 * @author arne
 */
@Stateless
@Path("persoon")
public class PersoonService {
    
   @Resource(name = "jdbc/communityshare")
    private DataSource source;
    private Persoon Pers;
    
    
    
	
	

@GET
@Produces(MediaType.APPLICATION_JSON)
	public List<Persoon> geefScores() 
	{
		List<Persoon> PersoonLijst = new ArrayList<>();

		// create Statement for querying database
		
		
		try(Connection conn = source.getConnection()) 
                {
			Statement s = conn.createStatement();
			

			
			ResultSet rs = s.executeQuery("SELECT FacebookAccount,TwitterAccount,Score FROM persoon ORDER BY Score ");
			
			while (rs.next()) 
                        {
                                 int persoonNr = 0;
                                  String twitac;
                                  twitac =rs.getString("TwitterAccount");
                                 if(twitac == null)
                                 {
                                    String twitterAccount = "geen";
                                     Pers = new Persoon(persoonNr, rs.getInt("Score"), rs.getString("FacebookAccount"), twitterAccount);
                                 }
                                 else
                                 {
                                     String facebookAccount = "geen";
                                     Pers = new Persoon(persoonNr, rs.getInt("Score"), facebookAccount,rs.getString("TwitterAccount"));
                                 }
				PersoonLijst.add(Pers);
				
				
					
			}
			return PersoonLijst;
                        
		} 
                catch (SQLException ex) 
                {
                              throw new WebApplicationException(ex);
                }
		
	}
	

@POST
@Consumes(MediaType.APPLICATION_JSON)
		public void voegEenPersoonToe(Persoon Pers) {


			try(Connection conn = source.getConnection()){
	
					
					
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Persoon VALUES(?,?,?)");
				
                                
				pstmt.setString(1, Pers.getFacebookAccount());
                                pstmt.setString(2, Pers.getTwitterAccount());
                                pstmt.setInt(3, Pers.getScore());
				
				pstmt.executeUpdate();
			}

			catch (SQLException ex) {
                              throw new WebApplicationException(ex);
                        }
		} 
			
	
    @Path("{account}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Persoon logIn(@PathParam("account")String facebookAccount, @PathParam("account")String twitterAccount)
	{
	try(Connection c = source.getConnection())
             {
		
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM persoon WHERE FacebookAccount ='"+facebookAccount+"' OR TwitterAccount='"+twitterAccount+"'");
		rs.next();
		
                
			
		if(rs.getString("FacebookAccount").equals(facebookAccount))
		{
                    twitterAccount = null;
                    Pers = new Persoon(rs.getInt("PersoonNr"), rs.getInt("Score"), rs.getString("FacebookAccount"),twitterAccount);
	
		}
                else if(rs.getString("TwitterAccount").equals(twitterAccount))
                {
                     facebookAccount = null;
                    Pers = new Persoon(rs.getInt("PersoonNr"), rs.getInt("Score"), facebookAccount,rs.getString("TwitterAccount"));
                }
	   }
	    catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
		return Pers;

	}
	

    
}
