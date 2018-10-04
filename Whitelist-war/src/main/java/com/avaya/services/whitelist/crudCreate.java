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
import org.apache.openjpa.persistence.RollbackException;

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
 * Servlet implementation class crudCreate
 * 
 **/
@WebServlet("/crudCreate")
public class crudCreate extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    private final Logger logger;       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public crudCreate() 
    {
        super();
        logger = Logger.getLogger(getClass());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * THe Http request will look like this in order to get to this entry point:
	 * 
	 * https://135.169.23.224/services/AAAihedDBv1/crudCreate?stringmembernum=1301
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        PrintWriter out = response.getWriter();
        out.println("Hello crudCreate");
		String stringmembernum = request.getParameter("stringmembernum");
		Integer membernum = Integer.valueOf(stringmembernum);
        out.println("Numero de cuenta " + membernum);
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

    		logger.info("crudCreate: emfactory and entityManager done");
    		
    		entityManager.getTransaction().begin();
    		logger.info("crudCreate:right after entityManager.getTransaction().begin() ");
    		
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

    		logger.info("crudCreate: right after team.set section");
    		
            try {
                entityManager.persist(team);
            } catch (IllegalStateException ie) {
                logger.info("crudCreate: Failed to create " + team + "\n" + ie.getMessage());
                out.println("Le informamos que su registro membernum = " + membernum + " no se pudo crear");
                out.println("Error = ie.getMessage = " + ie.getMessage());
            } catch (PersistenceException e) {
                logger.info("crudCreate: Failed to create " + team + "\n" + e.getMessage());
                out.println("Le informamos que su registro membernum = " + membernum + " no se pudo crear");
                out.println("Error = e.getMessage = " + e.getMessage() );
 
            } finally {
                logger.info("crudCreate: entityManager.Persist(team) ....worked");
            }
//************* End of saving    		
    		logger.info("crudCreate: right after entityManager.persist");
     		logger.info("crudCreate: team.getFirstname =  " + team.getFirstname());
     		logger.info("crudCreate: membernum =  " + membernum);     	     		
//    		entityManager.getTransaction().commit();
//     		logger.info("crudCreate: right after getTransaction.commit");
     		try {
     			if (entityManager.getTransaction().getRollbackOnly()){
     				entityManager.getTransaction().rollback();
     		          logger.info("Transaction rolled back");
     				}
     			
     		    	else { 
     		    		logger.info("Right before commit ");
     		    	entityManager.getTransaction().commit();
     	     		out.println("Le informamos que su registro membernum = " + membernum + " ha sido creado WorkSpace DBUtil");
     		    	}   
     		} catch (RollbackException e){
     			logger.info("this is the RollbackException after commit");
     			out.println("Le informamos que su registro membernum = " + membernum + " no ha sido creado, probablemente por que ya existia");
     		} finally {
     		entityManager.close();
     		logger.info("crudCreate: right after entityManager.close");
    		emfactory.close();
     		logger.info("crudCreate: right after emfactory.close");

     		}
     		}
}