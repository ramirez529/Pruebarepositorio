package com.avaya.services.whitelist.util;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.util.logger.Logger;

public final class EntityManagerFactoryImpl implements IEntityManagerFactory
{

    private Logger logger = Logger.getLogger(EntityManagerFactoryImpl.class);
    private static final String PERSISTANCE_UNIT = "whiteListDataSource";
    private EntityManagerFactory factory = null;
    private static EntityManagerFactoryImpl factoryImpl = new EntityManagerFactoryImpl();

    public static EntityManagerFactoryImpl getInstance()
    {
        return factoryImpl;
    }

    public EntityManagerFactory createEntityManagerFactory()
            throws NoAttributeFoundException, ServiceNotFoundException
    {
        logger.finer("createEntityManagerFactory ENTER");
        if (factoryImpl.factory == null)
        {
            logger.finest("createEntityManagerFactory creating entiry manager factory");
            final Map<String, String> persistentPropertiesMap = (new PersitanceUnitProperties())
                    .getPersistanceUnitMap();
            factoryImpl.factory = Persistence.createEntityManagerFactory(
                    PERSISTANCE_UNIT, persistentPropertiesMap);
        }
        return factoryImpl.factory;
    }

    public void destroyEntityManagerFactory()
    {
        logger.finer("destroyEntityManagerFactory ENTER");
        if (factoryImpl.factory != null)
        {
            factoryImpl.factory.close();
            factoryImpl.factory = null;
        }
    }

    public EntityManager initializeEntityManager()
            throws NoAttributeFoundException, ServiceNotFoundException
    {
        logger.finer("initializeEntityManager ENTER");
        createEntityManagerFactory();
        return factoryImpl.factory.createEntityManager();
    }

    public EntityManagerFactory getFactory()
    {
        return factory;
    }

    public void setFactory(final EntityManagerFactory factory)
    {
        this.factory = factory;
    }

}
