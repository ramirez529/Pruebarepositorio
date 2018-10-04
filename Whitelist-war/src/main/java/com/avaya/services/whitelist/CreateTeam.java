package com.avaya.services.whitelist;
//
//import java.util.HashMap;
//import java.util.Map;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import com.avaya.services.whitelist.db.Team;
//import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
//import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
//import com.avaya.collaboration.util.logger.Logger;
//import com.avaya.services.whitelist.util.EntityManagerFactoryImpl;
//import com.avaya.services.whitelist.util.IEntityManagerFactory;
//import com.avaya.services.whitelist.util.PersitanceUnitProperties;
//
//public class CreateTeam {
//	private final Logger logger;
//
//	public CreateTeam() {
//		this.logger = Logger.getLogger(CreateTeam.class);
//		logger.finest("createEntityManagerFactory creating entiry manager factory");
//		// Persistent properties
//		final Map<String, String> persistenceProperties = new HashMap<String, String>();
//		persistenceProperties.put("transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
//		persistenceProperties.put("openjpa.ConnectionUserName", "postgres");
//		persistenceProperties.put("openjpa.ConnectionPassword", "Breezeisfun2demo!");
//		persistenceProperties.put("openjpa.ConnectionDriverName", "org.apache.commons.dbcp.BasicDataSource");
//		persistenceProperties.put("openjpa.ConnectionProperties",
//				"DriverClassName=org.postgresql.xa.PGXADataSource,url=jdbc:postgresql://10.0.0.12:5432/postgres");
//		persistenceProperties.put("openjpa.DataCache", "true");
//		persistenceProperties.put("openjpa.QueryCache", "true(CacheSize=1000, SoftReferenceSize=0)");
//		persistenceProperties.put("openjpa.ConnectionMaxActive", "100");
//		persistenceProperties.put("openjpa.ConnectionMaxWait", "10000");
//		persistenceProperties.put("openjpa.ConnectionTestOnBorrow", "true");
//		// Create EntityManagerFactory
//		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TeamDataSource", persistenceProperties);
//
//		// Create EntityManager
//		EntityManager entityManager = emfactory.createEntityManager();
//
//		// MODIFICACION IMPORTANTE - WE WILL USE THE BUILT IN
//		// mod-war>Java Resources> src/main/java>
//		// com.avaya.services.whitelist.util>
//		// PersistanceUnitProperties and
//		// EntityManagerFactory
//		entityManager.getTransaction().begin();
//
//		Team team = new Team(0, null, null, null, null, null, null, null, null);
//		team.setMembernum(1201);
//		team.setFirstname("Manuel");
//		team.setLastname("Ramirez");
//		team.setEmail("jlramirez@avaya.com");
//		team.setPhone("17863310405");
//		team.setTeam("Engineering");
//		team.setMobilecarrier("AT&T");
//		team.setPreference("both");
//		team.setZipcode("33178");
//
//		entityManager.persist(team);
//		entityManager.getTransaction().commit();
//		entityManager.close();
//		emfactory.close();
//
//	}
//}