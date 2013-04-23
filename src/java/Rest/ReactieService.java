/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;


import Domein.Reactie;
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
    @Path("reactie")
public class ReactieService {
    
    @Resource(name = "jdbc/communityshare")
    private DataSource source;
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
	public void aanmakenVanEenReactie(Reactie r) 
	{
               
                if(r.getEventNr()!=0)
                {
                         int x = 0;
                     try( Connection conn = source.getConnection())
                        {
                        		
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO reactie("
                                + "EventNr,"
                                //+ "GevaarNr,"
                                + "ReactieNr,"
                                + "PersoonNr,"
                                + "Reactie,"
                                + "Datum)"
                                + "VALUES(?,?,?,?,?)");
                        pstmt.setInt(1, r.getEventNr());
			//pstmt.setInt(2, r.getGevaarNr());
                        pstmt.setInt(2,x);
                        pstmt.setInt(3,r.getPersoonNr());
                        pstmt.setString(4,r.getReactie());
                        pstmt.setDate(5,r.getDatum());
		
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
                        		
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO reactie("
                                //+ "EventNr,"
                                + "GevaarNr,"
                                + "ReactieNr,"
                                + "PersoonNr,"
                                + "Reactie,"
                                + "Datum)"
                                + "VALUES(?,?,?,?,?)");
                        //pstmt.setInt(1, r.getEventNr());
			pstmt.setInt(1, r.getGevaarNr());
                        pstmt.setInt(2,x);
                        pstmt.setInt(3,r.getPersoonNr());
                        pstmt.setString(4,r.getReactie());
                        pstmt.setDate(5,r.getDatum());
		
			pstmt.executeUpdate();
			
		}
                        
		catch (SQLException ex) 
		{
			throw new WebApplicationException(ex);
		}
                    
                }
	}     
           

@Path("{ReactieNr},{PersoonNr}")
  @DELETE
	public void verwijderenVanEenReactie(@PathParam("ReactieNr")int reactieNr, @PathParam("PersoonNr")int persoonNr) 
	{

			try(Connection conn = source.getConnection())
                        {
                                PreparedStatement stat = conn.prepareStatement("SELECT * FROM reactie WHERE ReactieNr = ?");
                                stat.setInt(1, reactieNr);
                                ResultSet rs = stat.executeQuery();
                                     if (!rs.next()) 
                                     {
                                         throw new WebApplicationException(Response.Status.NOT_FOUND);
                                     }
          		
                                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM reactie WHERE ReactieNr= ? AND PersoonNr = ?")) 
                                {
                                    pstmt.setInt(1, reactieNr);
                                    pstmt.setInt(2,persoonNr);
                                    pstmt.executeUpdate();
                                }										
                        
			  }
                        catch (SQLException ex) 
                          {
                            throw new WebApplicationException(ex);
                          }
      }
   
    @GET
    @Path("{gevaarNr}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reactie> geefLijstReactiesGevaar(@PathParam("gevaarNr")int gevaarNr) 
        {

           try(Connection conn = source.getConnection()) 
        
           {
               List<Reactie> reactieLijstGevaar = new ArrayList<>();
               PreparedStatement pstmt = conn.prepareStatement("SELECT PersoonNr, Reactie, ReactieNr FROM reactie WHERE GevaarNr = ? ORDER BY Datum" );
               pstmt.setInt(1, gevaarNr);
               ResultSet rs = pstmt.executeQuery();
                  
                     Date datum=null;
                     int eventNr = 0;
                  
                  while(rs.next())
                  {
                            
                          Reactie re = new Reactie(eventNr,gevaarNr,rs.getInt("PersoonNr"),rs.getInt("ReactieNr"), rs.getString("Reactie"),datum);
                          reactieLijstGevaar.add(re);
                  }
                  
                   return reactieLijstGevaar;
           }
            
	    catch (SQLException ex) 
            {
                throw new WebApplicationException(ex);
            }
          

      }
    
    
    @GET
    @Path("reactie/{eventNr}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reactie> geefLijstReactiesEvent(@PathParam("eventNr")int eventNr) 
	{
           try(Connection conn = source.getConnection()) 
           {
                    List<Reactie> reactieLijstGevaar = new ArrayList<>();
                    PreparedStatement pstmt = conn.prepareStatement("SELECT PersoonNr, Reactie, ReactieNr FROM reactie WHERE eventNr = ? ORDER BY Datum" );
                    pstmt.setInt(1, eventNr);
                    ResultSet rs = pstmt.executeQuery();
                  
                            
                            Date datum=null;
                            int gevaarNr = 0;
                  
                     while(rs.next())
                     {
                          
                          Reactie re = new Reactie(eventNr,gevaarNr,rs.getInt("PersoonNr"),rs.getInt("ReactieNr"), rs.getString("Reactie"),datum);
                          reactieLijstGevaar.add(re);
                     }
                     
                  return reactieLijstGevaar;
           }
            catch (SQLException ex) 
            {
                throw new WebApplicationException(ex);
            }
 
        }
   }

    
 
    
    
    
