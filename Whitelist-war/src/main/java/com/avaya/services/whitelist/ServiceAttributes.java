/////////////////////////////////////////////////////////////////////////////
// Copyright Avaya Inc., All Rights Reserved
// THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF AVAYA INC
// The copyright notice above does not evidence any
// actual or intended publication of such source code.
// Some third-party source code components may have been modified from
// their original versions by Avaya Inc.
// The modifications are Copyright Avaya Inc., All Rights Reserved.
//////////////////////////////////////////////////////////////////////////////
package com.avaya.services.whitelist;

import com.avaya.collaboration.service.ServiceLifeCycle;
import com.avaya.collaboration.service.TheServiceLifeCycle;
import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.util.EntityManagerFactoryImpl;

/**
 * The annotation @ServiceContext cause the Avaya Breeze to:
 * 
 * 1. Invoke the service lifecycle methods.
 * 
 * 2. Inject resources such as data access object (DAO).
 * 
 * @author Avaya
 * @version 1.0
 */
@TheServiceLifeCycle
public class ServiceAttributes implements ServiceLifeCycle
{
    private static final Logger LOGGER = Logger.getLogger(ServiceAttributes.class);

    @Override
    public final void init()
    {
        LOGGER.finer("init ENTER Create EntityManagerFactory");

        try
        {
            EntityManagerFactoryImpl.getInstance().createEntityManagerFactory();
        }
        catch (final Exception e)
        {
            LOGGER.error("init Exception : Service initialization failed in EntityMangerFactory creation", e);
            throw new RuntimeException(e);
        }

        LOGGER.finest("init register DM");

        DMListener.getInstance().registerWithDao();
    }

    @Override
    public final void destroy()
    {
        DMListener.getInstance().deregisterWithDao();
        EntityManagerFactoryImpl.getInstance().destroyEntityManagerFactory();
    }

}
