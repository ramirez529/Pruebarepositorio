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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.avaya.collaboration.call.Participant;
import com.avaya.collaboration.businessdata.api.ServiceData;

public final class AlternateDestinationFinderImplTest
{
    private AlternateDestinationFinder alternateDestinationFinder;
    private ServiceData serviceData;

    private Participant called;

    private static final String DEFAULT_ADDRESS = "+17205551212";

    private static final String REDIRECT_NUMBER_ATTRIBUTE =
            AlternateDestinationFinderImpl.REDIRECT_NUMBER_ATTRIBUTE;

    @Before
    public void setUp()
    {
        serviceData = mock(ServiceData.class);
        called = mock(Participant.class);

        when(called.getAddress()).thenReturn(DEFAULT_ADDRESS);

        alternateDestinationFinder = new AlternateDestinationFinderImpl(serviceData);
    }

    @Test
    public void getAlternateDestination_noRedirectNumberInDao() throws Exception
    {
        when(serviceData.getServiceAttribute(DEFAULT_ADDRESS, REDIRECT_NUMBER_ATTRIBUTE))
                .thenReturn(null);
        assertEquals(DEFAULT_ADDRESS, alternateDestinationFinder.getAlternateDestination(called));
    }

    @Test
    public void getAlternateDestination_redirectNumberInDao() throws Exception
    {
        final String ADMINISTERED_DESTINATION = "+17205551212";

        when(serviceData.getServiceAttribute(DEFAULT_ADDRESS, REDIRECT_NUMBER_ATTRIBUTE))
                .thenReturn(ADMINISTERED_DESTINATION);
        assertEquals(ADMINISTERED_DESTINATION, alternateDestinationFinder.getAlternateDestination(called));
    }
}
