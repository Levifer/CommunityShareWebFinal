/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Domein.Like;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author arne
 */
@Stateless
@Path("like")
public class LikeService {
    
    @Resource(name = "jdbc/communityshare")
    private DataSource source;
   
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void aanmakenVanEenLike(Like l) 
    {
       
        Statement statement;
        try( Connection conn = source.getConnection())
        {
            statement = conn.createStatement();
            String sql=("INSERT INTO liken( "
                    + "EventNr,"
                    + "GevaarNr,"
                    + "PersoonNr,"
                    + "Liken )"
                    + "VALUES(?,?,?,?)");
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,l.getEventNr());
            pstmt.setInt(2, l.getGevaarNr());
            pstmt.setInt(3,l.getPersoonNr());
            pstmt.setBoolean(4,l.isLiken());
            
            pstmt.executeUpdate();
           
        
         } 
        catch (SQLException ex)
         {
            throw new WebApplicationException(ex);
         }

          
    }
    
  @Path("{LikeNr},{PersoonNr}")
  @DELETE
	public void verwijderenVanEenLike(@PathParam("LikeNr")int LikeNr,@PathParam("persoonNr")int persoonNr) 
	{

			try(Connection conn = source.getConnection())
                        {
                                PreparedStatement stat = conn.prepareStatement("SELECT * FROM liken WHERE likeNr = ?");
                                stat.setInt(1, LikeNr);
                                ResultSet rs = stat.executeQuery();
                                     if (!rs.next()) 
                                     {
                                         throw new WebApplicationException(Response.Status.NOT_FOUND);
                                     }
          		
                                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM liken WHERE LikeNr= ? AND PersoonNr= ?")) 
                                {
                                    pstmt.setInt(1, LikeNr);
                                    pstmt.setInt(2, persoonNr);
                                    pstmt.executeUpdate();
                                }										
                        
			  }
                        catch (SQLException ex) 
                          {
                            throw new WebApplicationException(ex);
                          }
      }
}
