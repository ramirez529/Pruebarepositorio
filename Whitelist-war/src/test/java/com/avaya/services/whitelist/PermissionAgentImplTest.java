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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.avaya.collaboration.call.Participant;
import com.avaya.services.whitelist.db.WhiteListDao;

//public final class PermissionAgentImplTest
//{
//    private PermissionAgent permissionAgent;
//    private WhiteListDao whiteListDao;
//
//    private Participant caller;
//    private Participant called;
//
//    private static final String CALLING_PARTICIPANT_ADDRESS = "+15055551212@avaya.com";
//    private static final String CALLED_PARTICIPANT_ADDRESS = "+13035551212@avaya.com";
//
//    @Before
//    public void setUp()
//    {
//        whiteListDao = mock(WhiteListDao.class);
//        caller = mock(Participant.class);
//        called = mock(Participant.class);
//
//        permissionAgent = new PermissionAgentImpl(whiteListDao);
//
//        when(called.getAddress()).thenReturn(CALLED_PARTICIPANT_ADDRESS);
//        when(caller.getAddress()).thenReturn(CALLING_PARTICIPANT_ADDRESS);
//    }
//
//    @Test
//    public void isCallerAllowedToRouteToCalled_handleIsWhiteListed()
//    {
//        when(whiteListDao.isWhiteListedNumber(caller.getHandle(), called.getHandle())).thenReturn(true);
//
//        assertTrue(permissionAgent.isCallerAllowedToRouteToCalled(caller, called));
//    }
//
//    @Test
//    public void isCallerAllowedToRouteToCalled_handleIsNotWhiteListed()
//    {
//        when(whiteListDao.isWhiteListedNumber(caller.getHandle(), called.getHandle())).thenReturn(false);
//
//        assertFalse(permissionAgent.isCallerAllowedToRouteToCalled(caller, called));
//    }
//}
