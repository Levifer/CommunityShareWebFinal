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
    public void aanmakenVanEenReactie(Reactie r){

        Statement statement;
        try(Connection conn = source.getConnection()){
            statement = conn.createStatement();
            String sql=("INSERT INTO reactie( "
                    + "EventNr,"
                    + "GevaarNr,"
                    + "PersoonNr,"
                    + "Reactie "
                    + "Datum)"
                    + "VALUES(?,?,?,?,?)");
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,r.getEventNr());
            pstmt.setInt(2, r.getGevaarNr());
            pstmt.setInt(3,r.getPersoonNr());
            pstmt.setString(4,r.getReactie());
            pstmt.setDate(5, r.getDatum());
            
            pstmt.executeUpdate();
            
        }  
             catch (SQLException ex) {
                              throw new WebApplicationException(ex);
                        }
        //reactie verwijderen
    }
    
      @Path("{id}")
      @DELETE
    public void verwijderenVanEenReactie(@PathParam("id") Reactie r){
       
        
        try(Connection conn = source.getConnection()){
             Statement statement;
            statement= conn.createStatement();
            String sql= ("DELETE FROM reactie WHERE("
                    + "EventNr,"
                    + "GevaarNr,"
                    + "PersoonNr,"
                    + "Reactie )"
                    + "VALUES(?,?,?,?)");
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, r.getEventNr());
             pstmt.setInt(2, r.getGevaarNr());
             pstmt.setInt(3, r.getPersoonNr());
             pstmt.setString(4, r.getReactie());
             pstmt.executeUpdate();
             pstmt.close();
             
            
        }   
       catch (SQLException ex) {
                              throw new WebApplicationException(ex);
                        }
     
     }
    
    //lijst/zoeken = meldingnr meegeven, moet lijst geven van die reactie op die melding
      @GET
      @Produces(MediaType.APPLICATION_JSON)
    public List<Reactie> geefLijstReactiesGevaar(int gevaarNr) throws SQLException 
	{

           try(Connection conn = source.getConnection()) {
               List<Reactie> reactieLijstGevaar = new ArrayList<Reactie>();
                    Statement statement;
                     statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT PersoonNr, Reactie FROM reactie WHERE "
                           + "GevaarNr = '"+gevaarNr+"' "
                           + "ORDER BY Datum");
                  
                  
                  
                  while(rs.next()){
                            Date datum=null;
                            int eventNr = 0;
                          Reactie re = new Reactie(eventNr,gevaarNr,rs.getInt("PersoonNr"), rs.getString("Reactie"),datum);
                          reactieLijstGevaar.add(re);
                  }
                   return reactieLijstGevaar;
           }
            
	    catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
          

        }
      @GET
      @Produces(MediaType.APPLICATION_JSON)
        public List<Reactie> geefLijstReactiesEvent(int eventNr) 
	{
           try(Connection conn = source.getConnection()) {
                    List<Reactie> reactieLijstGevaar = new ArrayList<Reactie>();
                  Statement statement;
                  statement = conn.createStatement();
                  ResultSet rs = statement.executeQuery("SELECT PersoonNr, Reactie FROM reactie WHERE "
                           + "EventNr = '"+eventNr+"' "
                           + "ORDER BY Datum");
                  
                  
                  
                  while(rs.next()){
                            Date datum=null;
                            int gevaarNr = 0;
                          Reactie re = new Reactie(eventNr,gevaarNr,rs.getInt("PersoonNr"), rs.getString("Reactie"),datum);
                          reactieLijstGevaar.add(re);
                  }
                  return reactieLijstGevaar;
           }
            catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
 
        }
   }

    
 
    
    
    
