package com.avaya.services.whitelist.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceData;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.dal.factory.CollaborationDataFactory;
import com.avaya.collaboration.util.logger.Logger;
import com.avaya.zephyr.platform.dal.api.ServiceUtil;

public final class PersitanceUnitProperties {
	private final ServiceData serviceData;
	public static final String FACTORY_CLASS = "org.hibernate.transaction.JDBCTransactionFactory";
	public static final String DATA_CACHE = "true";
	public static final String QUERY_CACHE = "true(CacheSize=1000, SoftReferenceSize=0)";

	public static final String DB_TYPE_ATTRIBUTE = "dbType";
	public static final String DB_URL_ATTRIBUTE = "dbUrl";
	public static final String DB_USERNAME_ATTRIBUTE = "dbUsername";
	public static final String DB_PASSWORD_ATTRIBUTE = "dbPassword";
	public static final String DB_MAXACTIVE_ATTRIBUTE = "maxActive";
	public static final String DB_MAXWAIT_ATTRIBUTE = "maxWait";

	public static final String POSTGRES_DB_TYPE = "postgres";
	public static final String MYSQL_DB_TYPE = "mysql";
	private final Logger logger;

	public PersitanceUnitProperties() {
		this(CollaborationDataFactory.getServiceData(ServiceUtil
				.getServiceDescriptor().getName(), ServiceUtil
				.getServiceDescriptor().getVersion()), Logger
				.getLogger(PersitanceUnitProperties.class));
	}

	PersitanceUnitProperties(final ServiceData serviceData, final Logger logger) {
		this.serviceData = serviceData;
		this.logger = logger;
	}

	Map<String, String> getPersistanceUnitMap()
			throws NoAttributeFoundException, ServiceNotFoundException {
		final Map<String, String> persistenceProperties = new HashMap<String, String>();
		persistenceProperties.put("transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
		persistenceProperties.put("openjpa.ConnectionUserName", "postgres");
		persistenceProperties.put("openjpa.ConnectionPassword", "Breezeisfun2demo!");
		persistenceProperties.put("openjpa.ConnectionDriverName","org.apache.commons.dbcp.BasicDataSource");
		persistenceProperties.put("openjpa.ConnectionProperties","DriverClassName=org.postgresql.xa.PGXADataSource,url=jdbc:postgresql://10.0.0.12:5432/postgres");
		persistenceProperties.put("openjpa.DataCache", "true");
		persistenceProperties.put("openjpa.QueryCache", "true(CacheSize=1000, SoftReferenceSize=0)");
		persistenceProperties.put("openjpa.ConnectionMaxActive","100");
		persistenceProperties.put("openjpa.ConnectionMaxWait","10000");
		persistenceProperties.put("openjpa.ConnectionTestOnBorrow","true");
		return persistenceProperties;
	}

	private String getDbDriverName() throws NoAttributeFoundException,
			ServiceNotFoundException {
		final String postgresDriver = "org.postgresql.Driver";
		final String mysqlDriver = "com.mysql.jdbc.Driver";
		String driver = postgresDriver;

		final String dbType = serviceData
				.getServiceAttribute(DB_TYPE_ATTRIBUTE);
		if (StringUtils.isBlank(dbType)
				|| POSTGRES_DB_TYPE.equalsIgnoreCase(dbType.trim())) {
			driver = postgresDriver;
		} else if (MYSQL_DB_TYPE.equalsIgnoreCase(dbType.trim())) {
			driver = mysqlDriver;
		} else {
			throw new NoAttributeFoundException(
					"Invalid DB Type value is provided");
		}
		return driver;
	}

	private String getDbUrl() throws NoAttributeFoundException,
			ServiceNotFoundException {
		final String defaultDbUrl = "jdbc:postgresql://localhost/whitelist";
		String url = defaultDbUrl;

		final String dbUrl = serviceData.getServiceAttribute(DB_URL_ATTRIBUTE);
		if (!StringUtils.isBlank(dbUrl)) {
			url = dbUrl;
		}
		if (logger.isFinerEnabled()) {
			logger.finer("getDbUrl db url=" + url);
		}
		return url;
	}

	private String getDbUsername() throws NoAttributeFoundException,
			ServiceNotFoundException {
		final String defaultDbUserName = "postgres";
		String username = defaultDbUserName;

		final String dbUsername = serviceData
				.getServiceAttribute(DB_USERNAME_ATTRIBUTE);
		if (!StringUtils.isBlank(dbUsername)) {
			username = dbUsername;
		}
		return username;
	}

	private String getDbPassword() throws NoAttributeFoundException,
			ServiceNotFoundException {
		final String defaultDbPassword = "postgres";
		String password = defaultDbPassword;

		final String dbPassword = serviceData
				.getServiceEncryptedAttribute(DB_PASSWORD_ATTRIBUTE);
		if (!StringUtils.isBlank(dbPassword)) {
			password = dbPassword;
		}
		return password;
	}

	private String getMaxActiveDataBaseConnections()
			throws NoAttributeFoundException, ServiceNotFoundException {
		final String defaultMaxActive = "100";
		String maxActive = defaultMaxActive;

		final String dbMaxActive = serviceData
				.getServiceAttribute(DB_MAXACTIVE_ATTRIBUTE);
		if (!StringUtils.isBlank(dbMaxActive)) {
			maxActive = dbMaxActive;
		}
		return maxActive;
	}

	private String getMaxWaitForDataBaseConnection()
			throws NoAttributeFoundException, ServiceNotFoundException {
		final String defaultMaxWaitTimeInMs = "10000";
		String maxWait = defaultMaxWaitTimeInMs;

		final String dbMaxWait = serviceData
				.getServiceAttribute(DB_MAXWAIT_ATTRIBUTE);
		if (!StringUtils.isBlank(dbMaxWait)) {
			maxWait = dbMaxWait;
		}
		return maxWait;
	}

	private String getConnectionProperties() throws NoAttributeFoundException,
			ServiceNotFoundException {
		final StringBuffer connectionProperties = new StringBuffer(
				"DriverClassName=");
		connectionProperties.append(getDbDriverName());
		connectionProperties.append(",Url=");
		connectionProperties.append(getDbUrl());
		return connectionProperties.toString();
	}
}
