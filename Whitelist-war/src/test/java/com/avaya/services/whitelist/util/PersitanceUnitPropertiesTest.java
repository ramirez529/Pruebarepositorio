package com.avaya.services.whitelist.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceData;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.util.logger.Logger;

public class PersitanceUnitPropertiesTest {

	private PersitanceUnitProperties persistanceUnitProperties;
	private ServiceData serviceData;
	private Logger logger;

	@Before
	public void setup() {
		serviceData = mock(ServiceData.class);
		logger = Logger.getLogger(PersitanceUnitPropertiesTest.class);
		persistanceUnitProperties = new PersitanceUnitProperties(serviceData,
				logger);
	}

	@Test
	public void testGetPersistanceUnitMapPostgres()
			throws NoAttributeFoundException, ServiceNotFoundException {
		when(
				serviceData
						.getServiceAttribute(PersitanceUnitProperties.DB_TYPE_ATTRIBUTE))
				.thenReturn(PersitanceUnitProperties.POSTGRES_DB_TYPE);
		when(
				serviceData
						.getServiceAttribute(PersitanceUnitProperties.DB_URL_ATTRIBUTE))
				.thenReturn("jdbc:postgresql://localhost/whitelist");
		when(
				serviceData
						.getServiceAttribute(PersitanceUnitProperties.DB_USERNAME_ATTRIBUTE))
				.thenReturn("postgres");
		when(
				serviceData
						.getServiceEncryptedAttribute(PersitanceUnitProperties.DB_PASSWORD_ATTRIBUTE))
				.thenReturn("postgres");
		when(
				serviceData
						.getServiceAttribute(PersitanceUnitProperties.DB_MAXACTIVE_ATTRIBUTE))
				.thenReturn("100");
		when(
				serviceData
						.getServiceAttribute(PersitanceUnitProperties.DB_MAXWAIT_ATTRIBUTE))
				.thenReturn("10000");
		Map<String, String> postgresMap = persistanceUnitProperties
				.getPersistanceUnitMap();

		Assert.assertEquals("org.apache.commons.dbcp.BasicDataSource",
				postgresMap.get("openjpa.ConnectionDriverName"));
		Assert.assertEquals("postgres",
				postgresMap.get("openjpa.ConnectionUserName"));
		Assert.assertEquals("postgres",
				postgresMap.get("openjpa.ConnectionPassword"));
		Assert.assertEquals(PersitanceUnitProperties.FACTORY_CLASS,
				postgresMap.get("transaction.factory_class"));
		Assert.assertEquals(PersitanceUnitProperties.DATA_CACHE,
				postgresMap.get("openjpa.DataCache"));
		Assert.assertEquals(PersitanceUnitProperties.QUERY_CACHE,
				postgresMap.get("openjpa.QueryCache"));
		Assert.assertEquals("100",
				postgresMap.get("openjpa.ConnectionMaxActive"));
		Assert.assertEquals("10000",
				postgresMap.get("openjpa.ConnectionMaxWait"));
		Assert.assertEquals(
				"DriverClassName=org.postgresql.Driver,Url=jdbc:postgresql://localhost/whitelist",
				postgresMap.get("openjpa.ConnectionProperties"));
	}

}
