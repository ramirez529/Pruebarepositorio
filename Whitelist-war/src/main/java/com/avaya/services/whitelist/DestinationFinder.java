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

import javax.ejb.Local;

import com.avaya.collaboration.call.Participant;

@Local
public interface DestinationFinder
{
    /**
     * This method retrieves the desired destination when the calling party
     * calls the called party. The destination may or may not be that of the
     * called party.
     * 
     * @param caller
     *            the calling participant
     * @param called
     *            the called participant
     * @return The desired destination in a handle@domain format. E.g.,
     *         +17205551212@example.com
     */
    String getDestination(Participant caller, Participant called) throws Exception;
}
