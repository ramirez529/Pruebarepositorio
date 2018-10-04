package com.avaya.services.whitelist;

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
import com.avaya.zephyr.platform.dm.AbstractDMListener;

public class DMListener extends AbstractDMListener
{
    private final Logger logger;

    private static final DMListener DM_LISTENER = new DMListener();

    public DMListener()
    {
        logger = Logger.getLogger(DMListener.class);
    }

    public static DMListener getInstance()
    {
        return DM_LISTENER;
    }

    @Override
    public final void objectChanged(final Object oldObject, final Object newObject)
    {
        logger.finer("objectChanged ENTER");
        // when attribute value is updated
        if (oldObject != null && newObject != null)
        {
            logger.finer("objectChanged update");
            updateObjectChanged(oldObject, newObject);
        }
        // when attribute value is deleted
        else if (oldObject != null)
        {
            logger.finer("objectChanged delete");
            insertDeleteObjectChanged(oldObject);
        }
        // when attribute value is inserted
        else if (newObject != null)
        {
            logger.finer("objectChanged insert");
            insertDeleteObjectChanged(newObject);
        }
        logger.finer("objectChanged EXIT");
    }

    public final void insertDeleteObjectChanged(final Object object)
    {
        // when attribute values are changed in "Service Globals" tab
        // and Object will be instance of "DefaultAttribute"
        if (object instanceof DefaultAttribute)
        {
            final DefaultAttribute attribute = (DefaultAttribute) object;
            if (isFactoryDestroyRequired(attribute.getAttributeName()))
            {
                destroyFactoryOnAttributeChange();
            }
        }
        // when attribute values are changed in "Service Cluster" tab
        // and Object will be instance of "ClusterDefaultAttribute"
        else if (object instanceof ClusterDefaultAttribute)
        {
            final ClusterDefaultAttribute attribute = (ClusterDefaultAttribute) object;
            if (isFactoryDestroyRequired(attribute.getAttributeName()))
            {
                destroyFactoryOnAttributeChange();
            }
        }
    }

    public final void updateObjectChanged(final Object oldObject, final Object newObject)
    {
        // when attribute values are changed in "Service Globals" tab, oldObject
        // and newObject will be instance of "DefaultAttribute"
        if (oldObject instanceof DefaultAttribute || newObject instanceof DefaultAttribute)
        {
            final DefaultAttribute oldAttribute = (DefaultAttribute) oldObject;
            final DefaultAttribute newAttribute = (DefaultAttribute) newObject;
            if (isFactoryDestroyRequired(oldAttribute.getAttributeName()) ||
                    isFactoryDestroyRequired(newAttribute.getAttributeName()))
            {
                destroyFactoryOnAttributeChange();
            }
        }
        // when attribute values are changed in "Service Cluster" tab, oldObject
        // and newObject will be instance of "ClusterDefaultAttribute"
        else if (oldObject instanceof ClusterDefaultAttribute || newObject instanceof ClusterDefaultAttribute)
        {
            final ClusterDefaultAttribute oldAttribute = (ClusterDefaultAttribute) oldObject;
            final ClusterDefaultAttribute newAttribute = (ClusterDefaultAttribute) newObject;
            if (isFactoryDestroyRequired(oldAttribute.getAttributeName()) ||
                    isFactoryDestroyRequired(newAttribute.getAttributeName()))
            {
                destroyFactoryOnAttributeChange();
            }
        }
    }

    private boolean isFactoryDestroyRequired(final String attributeName)
    {
        boolean reload = false;
        if (PersitanceUnitProperties.DB_TYPE_ATTRIBUTE.equals(attributeName))
        {
            reload = true;
        }
        else if (PersitanceUnitProperties.DB_URL_ATTRIBUTE.equals(attributeName))
        {
            reload = true;
        }
        else if (PersitanceUnitProperties.DB_PASSWORD_ATTRIBUTE.equals(attributeName))
        {
            reload = true;
        }
        else if (PersitanceUnitProperties.DB_USERNAME_ATTRIBUTE.equals(attributeName))
        {
            reload = true;
        }
        else if (PersitanceUnitProperties.DB_MAXACTIVE_ATTRIBUTE.equals(attributeName))
        {
            reload = true;
        }
        else if (PersitanceUnitProperties.DB_MAXWAIT_ATTRIBUTE.equals(attributeName))
        {
            reload = true;
        }
        return reload;
    }

    private void destroyFactoryOnAttributeChange()
    {
        logger.finer("destroyFactoryOnAttributeChange ENTER");
        final IEntityManagerFactory factory = EntityManagerFactoryImpl.getInstance();
        logger.finest("destroyFactoryOnAttributeChange destroy EntityManagerFactory");
        // destroying EMF
        factory.destroyEntityManagerFactory();
        // creating new EMF upfront to reduce the EM creation time on first EM create call
        try
        {
            logger.finest("destroyFactoryOnAttributeChange create EntityManagerFactory");
            factory.createEntityManagerFactory();
        }
        catch (final NoAttributeFoundException | ServiceNotFoundException e)
        {
            // This can be ignored as EMF creation will be reattempted on first EM create call
            logger.error("destroyFactoryOnAttributeChange entity manager factory creation failed because of service attributes");
        }
        catch (final Exception e)
        {
            // This can be ignored as EMF creation will be reattempted on first EM create call
            logger.error("destroyFactoryOnAttributeChange entity manager factory creation failed because of Persistent error");
        }
    }

    public final void registerWithDao()
    {
        logger.finer("registerWithDao ENTER registering with DAO");
        final DMFactory dmFactory = DMFactory.getInstance();
        // For changes from "Service Globals" and "Service Clusters", listener
        // should be registered with "AusServiceDAO"
        dmFactory.getDataMgr(AusServiceDAO.class).registerListener(DMListener.getInstance());
    }

    public final void deregisterWithDao()
    {
        logger.finer("deregisterWithDao ENTER deregistering with DAO");
        final DMFactory dmFactory = DMFactory.getInstance();
        dmFactory.getDataMgr(AusServiceDAO.class).removeListener(DMListener.getInstance());
    }

}
