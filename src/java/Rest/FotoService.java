
package Rest;

import Domein.Foto;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("foto")
public class FotoService
{
    @Resource(name = "jdbc/communityshare")
    private DataSource source;
    //private final static String LEES_Foto_SQL = "SELECT * FROM Foto Order by FotoNr asc";
    private Foto F;

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Foto geefFoto(@PathParam("id")int fotoNr) throws SQLException, IOException 
	{
		
		Statement statement;
		Connection conn = source.getConnection();
		
		try 
		{
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM foto WHERE FotoNr ='"+fotoNr+"'");
			
                        Blob Foto=rs.getBlob("Foto");				 
                        InputStream imageBlobStream=Foto.getBinaryStream();
                        BufferedImage foto=ImageIO.read(imageBlobStream);

			F = new Foto(rs.getInt("FotoNr"), foto);
			
			statement.close();
		} 
		
		catch (SQLException ex) 
		{
			throw new WebApplicationException(ex);
		}
		return F;
	}
    
    
     @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public int  nieuweFoto(Foto F) throws SQLException 
	{
             int primkey=0;
		Connection conn = source.getConnection();

		try
		{			
			
			Blob Foto=conn.createBlob();
			OutputStream afbeeldingStream=Foto.setBinaryStream(1);
			ImageIO.write(F.getFoto(),"jpg",afbeeldingStream);
			
			//Statement s = connecti.getConnection().createStatement();//connectie maken
			//ResultSet rs = s.executeQuery("SELECT FotoNr FROM Foto ORDER BY FotoNr desc");
			//rs.next();			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO foto(FotoNr,Foto) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, F.getFotoNr());
			pstmt.setBlob(2, Foto);
			
						
			pstmt.executeUpdate();
                        ResultSet rs = pstmt.getGeneratedKeys();
                        if (rs != null && rs.next()) {
                        primkey = rs.getInt(1);
                        }

		}

		catch (SQLException sqlException) 
		{
                        System.out.print("Database Error nieuwefoto");
                        sqlException.getMessage();
                        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                return primkey;
	} 


}
