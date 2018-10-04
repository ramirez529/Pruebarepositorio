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

import com.avaya.collaboration.call.Participant;
import com.avaya.collaboration.dal.factory.CollaborationDataFactory;
import com.avaya.collaboration.businessdata.api.ServiceData;
 

public final class AlternateDestinationFinderImpl implements AlternateDestinationFinder
{
    private final ServiceData serviceData;

    public static final String SERVICE_NAME = "Whitelist";
    public static final String SERVICE_VERSION = "2.0.0.0.0";
    public static final String REDIRECT_NUMBER_ATTRIBUTE = "redirectNumber";

    public AlternateDestinationFinderImpl()
    {
        this(CollaborationDataFactory.getServiceData(SERVICE_NAME,SERVICE_VERSION));
    }

    AlternateDestinationFinderImpl(final ServiceData serviceData)
    {
        this.serviceData = serviceData;
    }

    @Override
    public String getAlternateDestination(final Participant defaultDestination) throws Exception
    {
        final String defaultAddress = defaultDestination.getAddress();
        String redirectAddress = null;
        redirectAddress = serviceData.getServiceAttribute(defaultAddress, REDIRECT_NUMBER_ATTRIBUTE);

        return (redirectAddress != null) ? redirectAddress : defaultAddress;
    }
}
