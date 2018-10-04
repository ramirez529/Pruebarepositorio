package com.avaya.services.whitelist;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.openjpa.persistence.EntityExistsException;
import org.apache.openjpa.persistence.PersistenceException;

import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.db.Team;

/**
 * 
 * This class is needed if you are trying to make your application accessible through a HTTP servlet. 
 * Refer DynamicTeamFormation Sample service to understand more about how to use this.
 * 
 * For applications which provide call related features only and web service is not required, remove this class.
 * 
 * 
 * Servlet implementation class HelloServlet
 * 
 **/
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    private final Logger logger;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() 
    {
        super();
        logger = Logger.getLogger(getClass());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * THe Http request will look like this in order to get to this entry point:
	 * 
	 * https://135.169.23.224/services/AAAINotificationREST/MyServlet?stringmembernum=1301
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        PrintWriter out = response.getWriter();
        out.println("Hello world-1");
		String stringmembernum = request.getParameter("stringmembernum");
		Integer membernum = Integer.valueOf(stringmembernum);
        
        out.println("Numero de cuenta " + membernum);
//CREATE TEAM
        logger.info("MyServel: Creating PersistanceProperties");
    		// Persistent properties
    		final Map<String, String> persistenceProperties = new HashMap<String, String>();
    		persistenceProperties.put("transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
    		persistenceProperties.put("openjpa.ConnectionUserName", "postgres");
    		persistenceProperties.put("openjpa.ConnectionPassword", "Breezeisfun2demo!");
    		persistenceProperties.put("openjpa.ConnectionDriverName", "org.apache.commons.dbcp.BasicDataSource");
    		persistenceProperties.put("openjpa.ConnectionProperties",
    				"DriverClassName=org.postgresql.xa.PGXADataSource,url=jdbc:postgresql://10.0.0.12:5432/postgres");
    		persistenceProperties.put("openjpa.DataCache", "true");
    		persistenceProperties.put("openjpa.QueryCache", "true(CacheSize=1000, SoftReferenceSize=0)");
    		persistenceProperties.put("openjpa.ConnectionMaxActive", "100");
    		persistenceProperties.put("openjpa.ConnectionMaxWait", "10000");
    		persistenceProperties.put("openjpa.ConnectionTestOnBorrow", "true");
    		logger.info("MyServel:persistenceProperties = " + persistenceProperties);
    		// Create EntityManagerFactory
    		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TeamDataSource", persistenceProperties);

    		// Create EntityManager
    		EntityManager entityManager = emfactory.createEntityManager();

    		// MODIFICACION IMPORTANTE - WE WILL USE THE BUILT IN
    		// mod-war>Java Resources> src/main/java>
    		// com.avaya.services.whitelist.util>
    		// PersistanceUnitProperties and
    		// EntityManagerFactory
    		logger.info("MyServel: emfactory and entityManager dobe");
    		
    		entityManager.getTransaction().begin();
    		logger.info("MyServel:right after entityManager.getTransaction().begin() ");
    		
//    		Team team = new Team(0, null, null, null, null, null, null, null, null);
    		Team team = new Team();
    		team.setMembernum(membernum);
    		team.setFirstname("Manuel");
    		team.setLastname("Ramirez");
    		team.setEmail("jlramirez@avaya.com");
    		team.setHomephone("17863310405");
    		team.setTeam("Engineering");
    		team.setMobilecarrier("AT&T");
    		team.setPreference("both");
    		team.setZipcode("33178");

    		logger.info("MyServlet:right after team.set section");
    		
//    		entityManager.persist(team);
            try {
                entityManager.persist(team);
            } catch (IllegalStateException ie) {
                logger.info("Failed to update " + team + "\n" + ie.getMessage());
//            } catch (EntityExistsException e) {
//                throw new SQLException("Object already exists: " + e.getMessage());
            } catch (PersistenceException e) {
                logger.info("Failed to update " + team + "\n" + e.getMessage());
//                throw new SQLException("Unable to save record.");
//            } catch (Exception e) {
//                logger.info("Failed to persist " + team.toString() + "\n" + e.getMessage());
//                throw new SQLException("unable to save record");
            } finally {
                logger.info("Servlet: entityManager.Persist(team) ....worked");
            }
    		
    		
    		
    		
    		
    		
//************* End of saving    		
    		logger.info("MyServlet: right after entityManager.persist");
     		logger.info("MyServlet: " + team.getFirstname());
     		logger.info("MyServlet: " + membernum);     	     		
    		entityManager.getTransaction().commit();
     		logger.info("MyServlet: right after getTransaction");
    		entityManager.close();
     		logger.info("MyServlet: right after entityManager.close");
    		emfactory.close();
     		logger.info("MyServlet: right after emfactory.close");

    	}
        
        
        
        
//            HttpClient httpClient = new DefaultHttpClient();
//        HttpClient httpClient = HttpClientBuilder.create().build();        
//            URI url = null;
//			try {
//				url = new URI("https://breeze2-213.collaboratory.avaya.com/services/EventingConnector/events");
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        
//            HttpPost httpPost = new HttpPost(url);
//        
//            HttpEntity reqEntity = MultipartEntityBuilder.create()
//            .addPart("family", new StringBody("AAAIHED",ContentType.TEXT_PLAIN))
//            .addPart("type", new StringBody("SUBSCRIPTION_CONFIRMATION",ContentType.TEXT_PLAIN))
//            .addPart("version", new StringBody("1.0",ContentType.TEXT_PLAIN))
//            .addPart("metadata-user", new StringBody("userABCD",ContentType.TEXT_PLAIN))
//            .addPart("metadata-correlationId", new StringBody(param1,ContentType.TEXT_PLAIN))
//            .addPart("metadata-newData", new StringBody("someData",ContentType.TEXT_PLAIN))
//            .addPart("eventBody", new StringBody("{instanceId:48 , confirmation:true}",ContentType.TEXT_PLAIN))
//            .build();
//// *******Example of the last line but thru a JSON file upload
////      .addPart("eventBody", new FileBody(new File("C:\\Program Files\\Java\\jre7\\README.txt")).build();
//            
//        
//            httpPost.setEntity(reqEntity);
//        
//            HttpResponse response1 = httpClient.execute(httpPost);
//        
//            System.out.println(response1.getStatusLine());
//            out.println("Your request has been accepted and you will receive an email notifying you this is all done!");
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
	}
}