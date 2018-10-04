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

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.avaya.collaboration.call.Participant;
import com.avaya.collaboration.util.logger.Logger;

@Stateless
public class DestinationFinderImpl implements DestinationFinder
{
    private final AlternateDestinationFinder alternateDestinationFinder;
    private final Logger logger;

    @EJB
    private PermissionAgent permissionAgent;

    public DestinationFinderImpl()
    {
        this.alternateDestinationFinder = new AlternateDestinationFinderImpl();
        this.logger = Logger.getLogger(DestinationFinderImpl.class);
    }

    public DestinationFinderImpl(final PermissionAgent permissionAgent,
            final AlternateDestinationFinder alternateDestinationFinder, final Logger logger)
    {
        this.permissionAgent = permissionAgent;
        this.alternateDestinationFinder = alternateDestinationFinder;
        this.logger = logger;
    }

    /**
     * This method retrieves the desired destination when the calling party
     * calls the called party. The destination is based on whether the called
     * party has granted the calling party permission to call him or her
     * directly. If permission is granted, the destination is the called party.
     * If permission is not granted, the destination is based on an administered
     * value (alternate destination).
     * 
     * The permission table and the administered value for the destination are
     * stored in a database. In case of a database access failure, the
     * destination may be changed to the called party, whereas during normal
     * operations the destination would have resulted in the alternate
     * destination.
     */
    @Override
    public final String getDestination(final Participant caller, final Participant called) throws Exception
    {
        enterLog(caller, called);

        final String destination;
        if (permissionAgent.isCallerAllowedToRouteToCalled(caller, called))
        {
            destination = called.getAddress();
        }
        else
        {
            destination = alternateDestinationFinder.getAlternateDestination(called);
        }

        exitLog(caller, called, destination);
        return destination;
    }

    private void enterLog(final Participant caller, final Participant called)
    {
        if (logger.isFinerEnabled())
        {
            final StringBuilder enterLog = new StringBuilder("getDestination ENTER permissionAgent=");
            enterLog.append(permissionAgent);
            enterLog.append(" caller address=" + caller.getAddress());
            enterLog.append(" called address=" + called.getAddress());
            logger.finer(enterLog);
        }
    }

    private void exitLog(final Participant caller, final Participant called, final String destination)
    {
        if (logger.isFinerEnabled())
        {
            final StringBuilder exitLog = new StringBuilder("getDestination EXIT: The call from ");
            exitLog.append(caller.getAddress() + " to " + called.getAddress() + " is ");

            final String operation;
            if (destination == called.getAddress())
            {
                operation = "proxied";
            }
            else
            {
                operation = "redirected";
            }

            exitLog.append(operation + " to " + destination);
            logger.info(exitLog);
        }
    }
}
