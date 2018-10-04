package com.avaya.services.whitelist;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Assert;
import org.junit.Test;

import com.avaya.asm.datamgr.DMFactory;
import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.util.EntityManagerFactoryImpl;
import com.avaya.services.whitelist.util.IEntityManagerFactory;
import com.avaya.services.whitelist.util.PersitanceUnitProperties;
import com.avaya.zephyr.platform.dal.api.objectapi.ClusterDefaultAttribute;
import com.avaya.zephyr.platform.dal.api.objectapi.DefaultAttribute;
import com.avaya.zephyr.platform.dao.AusServiceDAO;

public class DMListenerTest
{

    DMListener dmListener = DMListener.getInstance();
    private final long id = 1;

    @Test
    public void objectChangedDbType()
    {
        DefaultAttribute oldObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_TYPE_ATTRIBUTE, "defaultValue",
                        "entValue");
        DefaultAttribute newObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_TYPE_ATTRIBUTE, "defaultValue",
                        "newentValue");
        dmListener.objectChanged(oldObject, newObject);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

    @Test
    public void objectChangedUsername()
    {
        DefaultAttribute oldObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_USERNAME_ATTRIBUTE, "defaultValue",
                        "entValue");
        DefaultAttribute newObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_USERNAME_ATTRIBUTE, "defaultValue",
                        "newentValue");
        dmListener.objectChanged(oldObject, newObject);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

    @Test
    public void objectChangedPwd()
    {
        DefaultAttribute oldObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_PASSWORD_ATTRIBUTE, "defaultValue",
                        "entValue");
        DefaultAttribute newObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_PASSWORD_ATTRIBUTE, "defaultValue",
                        "newentValue");
        dmListener.objectChanged(oldObject, newObject);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

    @Test
    public void objectChangedUrl()
    {
        DefaultAttribute oldObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_URL_ATTRIBUTE, "defaultValue",
                        "entValue");
        DefaultAttribute newObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_URL_ATTRIBUTE, "defaultValue",
                        "newentValue");
        dmListener.objectChanged(oldObject, newObject);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

    @Test
    public void objectChangedMaxActiveConnections()
    {
        DefaultAttribute oldObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_MAXACTIVE_ATTRIBUTE, "defaultValue",
                        "entValue");
        DefaultAttribute newObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_MAXACTIVE_ATTRIBUTE, "defaultValue",
                        "newentValue");
        dmListener.objectChanged(oldObject, newObject);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

    @Test
    public void objectChangedMaxWaitForDataBaseConnection()
    {
        ClusterDefaultAttribute oldObject =
                new ClusterDefaultAttribute(id, id, id, PersitanceUnitProperties.DB_MAXWAIT_ATTRIBUTE,
                        "clusValue");
        ClusterDefaultAttribute newObject =
                new ClusterDefaultAttribute(id, id, id, PersitanceUnitProperties.DB_MAXWAIT_ATTRIBUTE,
                        "newclusValue");
        dmListener.objectChanged(oldObject, newObject);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

    @Test
    public void deregisterWithDao()
    {
        dmListener.deregisterWithDao();
        DMFactory dmFactory = DMFactory.getInstance();
        // For changes from "Service Globals" and "Service Clusters", listener
        // should be registered with "AusServiceDAO"
        List<com.avaya.asm.datamgr.DMListener> list =
                dmFactory.getDataMgr(AusServiceDAO.class).getListeners();
        dmListener.deregisterWithDao();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void objectChangedCreateEMF() throws NoAttributeFoundException, ServiceNotFoundException
    {
        Logger logger = mock(Logger.class);
        // dmListener = new DMListener();
        final IEntityManagerFactory entityManagerFactory = EntityManagerFactoryImpl.getInstance();
        final EntityManagerFactory factory = mock(EntityManagerFactory.class);
        entityManagerFactory.setFactory(factory);
        final EntityManager entityManager = mock(EntityManager.class);

        when(entityManagerFactory.initializeEntityManager()).thenReturn(entityManager);

        when(logger.isFinerEnabled()).thenReturn(true);

        ClusterDefaultAttribute oldObject =
                new ClusterDefaultAttribute(id, id, id, PersitanceUnitProperties.DB_MAXWAIT_ATTRIBUTE,
                        "clusValue");
        ClusterDefaultAttribute newObject =
                new ClusterDefaultAttribute(id, id, id, PersitanceUnitProperties.DB_MAXWAIT_ATTRIBUTE,
                        "newclusValue");
        dmListener.objectChanged(oldObject, newObject);
        final EntityManagerFactory newFactory = EntityManagerFactoryImpl.getInstance().getFactory();
        Assert.assertNull(newFactory);
    }

    @Test
    public void oldObjectChangedMaxActiveConnections()
    {
        final DefaultAttribute oldObject =
                new DefaultAttribute(id, PersitanceUnitProperties.DB_MAXACTIVE_ATTRIBUTE, "defaultValue", "entValue");
        dmListener.objectChanged(oldObject, null);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

    @Test
    public void newObjectChangedMaxWaitForDataBaseConnection()
    {
        final ClusterDefaultAttribute newObject =
                new ClusterDefaultAttribute(id, id, id, PersitanceUnitProperties.DB_MAXWAIT_ATTRIBUTE, "newclusValue");
        dmListener.objectChanged(null, newObject);
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        Assert.assertNull(factory.getFactory());
    }

}
