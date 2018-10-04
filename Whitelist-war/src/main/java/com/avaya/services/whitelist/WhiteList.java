/////////////////////////////////////////////////////////////////////////////
//Copyright Avaya Inc., All Rights Reserved
// THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF AVAYA INC
// The copyright notice above does not evidence any
// actual or intended publication of such source code.
// Some third-party source code components may have been modified from
// their original versions by Avaya Inc.
// The modifications are Copyright Avaya Inc., All Rights Reserved.
//
//////////////////////////////////////////////////////////////////////////////
package com.avaya.services.whitelist;

import com.avaya.collaboration.call.Call;
import com.avaya.collaboration.call.CallListenerAbstract;
import com.avaya.collaboration.call.TheCallListener;
import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.util.EjbJndiLookup;
import com.avaya.services.whitelist.util.EjbJndiLookupImpl;
import com.avaya.zephyr.platform.dal.api.ServiceDescriptor;
import com.avaya.zephyr.platform.dal.api.ServiceUtil;

@TheCallListener
public final class WhiteList extends CallListenerAbstract
{
    private DestinationFinder destinationFinder;
    private boolean destinationFinderLocated;
    private EjbJndiLookup jndiLookup;
    private final Logger logger;
    private final ServiceDescriptor serviceDescriptor;

    public WhiteList()
    {
        this(ServiceUtil.getServiceDescriptor(), Logger.getLogger(WhiteList.class));
        destinationFinderLocated = false;
    }

    WhiteList(final ServiceDescriptor serviceDescriptor, final Logger logger)
    {
        this.serviceDescriptor = serviceDescriptor;
        this.logger = logger;
        try
        {
            this.jndiLookup = new EjbJndiLookupImpl();
        }
        catch (final Exception exception)
        {
            this.jndiLookup = null;
        }
    }

    WhiteList(final ServiceDescriptor serviceDescriptor, final EjbJndiLookup jndiLookup, final Logger logger)
    {
        this.serviceDescriptor = serviceDescriptor;
        this.jndiLookup = jndiLookup;
        this.logger = logger;
    }

    private void setDestinationFinder()
    {
        if (destinationFinderLocated)
        {
            return;
        }

        try
        {
            final String name = serviceDescriptor.getName();
            final String version = serviceDescriptor.getVersion();
            final String interfacePackageName = "com.avaya.services.whitelist.DestinationFinder";

            this.destinationFinder = (DestinationFinder) jndiLookup.get(name, version, interfacePackageName);
            destinationFinderLocated = true;
        }
        catch (final Exception exception)
        {
            this.destinationFinder = null;
            logger.error("setDestinationFinder Exception=" + exception, exception);
        }
    }

    @Override
    public void callIntercepted(final Call call)
    {
        setDestinationFinder();

        try
        {
            call.divertTo(destinationFinder.getDestination(call.getCallingParty(), call.getCalledParty()));
        }
        catch (final Exception exception)
        {
            logger.error("callIntercepted exception=" + exception, exception);
            call.allow();
        }
    }
}
