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
public interface PermissionAgent
{
    boolean isCallerAllowedToRouteToCalled(Participant caller, Participant called);
}
