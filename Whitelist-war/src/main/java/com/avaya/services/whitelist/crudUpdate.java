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
 * Servlet implementation class crudUpdate
 * 
 **/
@WebServlet("/crudUpdate")
public class crudUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public crudUpdate() {
		super();
		logger = Logger.getLogger(getClass());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) THe Http request will look like this in order to get to
	 *      this entry point:
	 * 
	 *      https://135.169.23.224/services/AAAihedDBv1/crudUpdate?
	 *      stringmembernum=1301&lastname=Ramiri
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String stringmembernum = request.getParameter("stringmembernum");
		String lastname = request.getParameter("lastname");
		Integer membernum = Integer.valueOf(stringmembernum);
		out.println("crudUpdate- Datos ingresados: Numero de cuenta " + membernum + " lastname = " + lastname);
		logger.info("crudUpdate: Creating PersistanceProperties");
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
		logger.info("crudUpdate:persistenceProperties = " + persistenceProperties);
		// Create EntityManagerFactory
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TeamDataSource",
				persistenceProperties);

		// Create EntityManager
		EntityManager entityManager = emfactory.createEntityManager();

		logger.info("crudUpdate: emfactory and entityManager done");

		entityManager.getTransaction().begin();
		logger.info("crudUpdate: right after entityManager.getTransaction().begin() ");
		// =========FIN DE Lineas comunes

		Team team = entityManager.find(Team.class, membernum);

		// before update
		out.println("crudUpdate - team before the update: " + team);
		team.setLastname(lastname);
		entityManager.getTransaction().commit();

		// after update
		out.println("crudUpdate - team after the update: " + team);


		entityManager.close();
		logger.info("MyServlet: right after entityManager.close");
		emfactory.close();
		logger.info("MyServlet: right after emfactory.close");

	}
}