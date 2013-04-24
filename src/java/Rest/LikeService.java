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
        
        if(l.getEventNr()!=0)
        {
       int x = 0;

        try( Connection conn = source.getConnection())
        {   
          
            
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO liken( "
                    + "LikeNr,"
                    + "EventNr,"
                   //+ "GevaarNr,"
                    + "Liken,"
                    + "PersoonNr)"
                    + "VALUES(?,?,?,?)");
            pstmt.setInt(1, x);
            pstmt.setInt(2,l.getEventNr());
            //pstmt.setInt(3, l.getGevaarNr());
            pstmt.setBoolean(3,l.isLiken());
            pstmt.setInt(4,l.getPersoonNr());
            
            pstmt.executeUpdate();
           
        
         } 
        catch (SQLException ex)
         {
            throw new WebApplicationException(ex);
         }
        }
        else
        {
                   
       int x = 0;

        try( Connection conn = source.getConnection())
        {   
          
            
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO liken( "
                    + "LikeNr,"
                    //+ "EventNr,"
                    + "GevaarNr,"
                    + "Liken,"
                    + "PersoonNr)"
                    + "VALUES(?,?,?,?)");
            pstmt.setInt(1, x);
            // pstmt.setInt(2,l.getEventNr());
            pstmt.setInt(2, l.getGevaarNr());
            pstmt.setBoolean(3,l.isLiken());
            pstmt.setInt(4,l.getPersoonNr());
            
            pstmt.executeUpdate();
           
        
         } 
        catch (SQLException ex)
         {
            throw new WebApplicationException(ex);
         }
        }

          
    }
    
  @Path("{LikeNr},{PersoonNr}")
  @DELETE
	public void verwijderenVanEenLike(@PathParam("LikeNr")int likeNr, @PathParam("PersoonNr")int persoonNr) 
	{

			try(Connection conn = source.getConnection())
                        {
                                PreparedStatement stat = conn.prepareStatement("SELECT * FROM liken WHERE LikeNr = ?");
                                stat.setInt(1, likeNr);
                                ResultSet rs = stat.executeQuery();
                                     if (!rs.next()) 
                                     {
                                         throw new WebApplicationException(Response.Status.NOT_FOUND);
                                     }
          		
                                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM liken WHERE LikeNr= ? AND PersoonNr = ?")) 
                                {
                                    pstmt.setInt(1, likeNr);
                                    pstmt.setInt(2,persoonNr);
                                    pstmt.executeUpdate();
                                }										
                        
			  }
                        catch (SQLException ex) 
                          {
                            throw new WebApplicationException(ex);
                          }
      }
}
