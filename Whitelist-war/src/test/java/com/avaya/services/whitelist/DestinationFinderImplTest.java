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
import com.avaya.collaboration.util.logger.Logger;

public final class DestinationFinderImplTest
{
    private DestinationFinder destinationFinder;
    private PermissionAgent permissionAgent;
    private AlternateDestinationFinder alternateDestinationFinder;
    private Logger logger;

    private Participant caller;
    private Participant called;

    private static final String ADMINISTERED_DESTINATION = "+17205551212@avaya.com";
    private static final String CALLING_PARTICIPANT_ADDRESS = "+15055551212@avaya.com";
    private static final String CALLED_PARTICIPANT_ADDRESS = "+13035551212@avaya.com";

    @Before
    public void setUp()
    {
        permissionAgent = mock(PermissionAgent.class);
        alternateDestinationFinder = mock(AlternateDestinationFinder.class);
        logger = mock(Logger.class);

        caller = mock(Participant.class);
        called = mock(Participant.class);

        destinationFinder =
                new DestinationFinderImpl(permissionAgent, alternateDestinationFinder, logger);

        when(caller.getAddress()).thenReturn(CALLING_PARTICIPANT_ADDRESS);
        when(called.getAddress()).thenReturn(CALLED_PARTICIPANT_ADDRESS);
        when(logger.isFinerEnabled()).thenReturn(true);
    }

    @Test
    public void getDestination_callerIsAllowedToRouteToCalled() throws Exception
    {
        when(permissionAgent.isCallerAllowedToRouteToCalled(caller, called)).thenReturn(true);

        assertEquals(CALLED_PARTICIPANT_ADDRESS, destinationFinder.getDestination(caller, called));
    }

    @Test
    public void getDestination_callerIsNotAllowedToRouteToCalled_administeredDestinationPresent() throws Exception
    {
        when(permissionAgent.isCallerAllowedToRouteToCalled(caller, called)).thenReturn(false);
        when(alternateDestinationFinder.getAlternateDestination(called)).thenReturn(ADMINISTERED_DESTINATION);

        assertEquals(ADMINISTERED_DESTINATION, destinationFinder.getDestination(caller, called));
    }

    @Test
    public void
            getDestination_callerIsNotAllowedToRouteToCalled_administeredDestinationIsNotPresent() throws Exception
    {
        when(permissionAgent.isCallerAllowedToRouteToCalled(caller, called)).thenReturn(false);
        when(alternateDestinationFinder.getAlternateDestination(called)).thenReturn(
                CALLED_PARTICIPANT_ADDRESS);

        assertEquals(CALLED_PARTICIPANT_ADDRESS, destinationFinder.getDestination(caller, called));
    }
}
