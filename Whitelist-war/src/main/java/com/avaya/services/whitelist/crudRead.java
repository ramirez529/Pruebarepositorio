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
 * This class is needed if you are trying to make your application accessible
 * through a HTTP servlet. Refer DynamicTeamFormation Sample service to
 * understand more about how to use this.
 * 
 * For applications which provide call related features only and web service is
 * not required, remove this class.
 * 
 * 
 * Servlet implementation class crudRead
 * 
 **/
@WebServlet("/crudRead")
public class crudRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public crudRead() {
		super();
		logger = Logger.getLogger(getClass());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) THe Http request will look like this in order to get to
	 *      this entry point:
	 * 
	 *      https://135.169.23.224/services/AAAihedDBv1/crudRead?stringmembernum
	 *      =1301
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("Hello world-1");
		String stringmembernum = request.getParameter("stringmembernum");
		Integer membernum = Integer.valueOf(stringmembernum);
		out.println("Numero de cuenta " + membernum);
		logger.info("crudRead: Creating PersistanceProperties");
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
		logger.info("crudRead: persistenceProperties = " + persistenceProperties);
		// Create EntityManagerFactory
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TeamDataSource",
				persistenceProperties);
		// Create EntityManager
		EntityManager entityManager = emfactory.createEntityManager();
		Team team = entityManager.find(Team.class, membernum);
		logger.info("crudRead: find done");
		out.println("Gracias, le informamos que el registro " + membernum + "ha sido leido");
		out.println("membernum = " + team.getMembernum());
		out.println("El registro completo = " + team);
	}

}