/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Domein;

import Rest.PersoonService;


/**
 *
 * @author arne
 */
public class ModelPersoon 
{
    private Persoon pl ;
    private PersoonService ad = new PersoonService();
		
		
    
    public ModelPersoon()
    {
			
			
    }
		
		
		

		public void LogInFacebook(String facebookAccount)
		{
                    String twitterAccount = "geen";
			
			try
                        {			
                            if(facebookAccount==null)
                            {
                                System.err.println("Er moet een juiste facebookAccount worden ingegeven");
                            }
                            else 
                            {
                                
                                pl = ad.logIn(facebookAccount, twitterAccount);//roept methode 
                            }
                        }
			catch(RuntimeException e)
                        {
				System.err.println("Het ingegeven facebookAccount is niet bij ons geregistreerd");
			}
			
		}
                
                public void LogInTwitter(String twitterAccount)
                {
                    String facebookAccount = null;
                    
                    try
                    {
                        if(twitterAccount == null)
                        {
                            System.err.println("Er moet een juiste twitterAccount worden ingegeven");
                        }
                        else
                        {
                            pl = ad.logIn(facebookAccount, twitterAccount);
                        }
                    }
                    catch(RuntimeException e)
                    {
                        System.err.println("Het ingegeven twitterAccount is niet bij ons geresistreerd");
                    }
                }

   
                
   public Persoon getPl() 
   {
       
                    
       return pl;
    
                
   }

    public void setPl(Persoon pl) 
    {
       
        this.pl = pl;
    }
		
		



		

	


		

		



}
    

