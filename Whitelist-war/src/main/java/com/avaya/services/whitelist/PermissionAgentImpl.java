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

import javax.ejb.Stateless;

import com.avaya.collaboration.call.Participant;
import com.avaya.services.whitelist.db.WhiteListDao;
//import com.avaya.services.whitelist.db.WhiteListDaoImpl;

//@Stateless
//public class PermissionAgentImpl implements PermissionAgent
//{
//    private WhiteListDao whiteListDao;
//
//    public PermissionAgentImpl()
//    {
//    	whiteListDao = new WhiteListDaoImpl();
//    }
//
//    PermissionAgentImpl(final WhiteListDao whiteListDao)
//    {
//        this.whiteListDao = whiteListDao;
//    }
//
//    @Override
//    public final boolean isCallerAllowedToRouteToCalled(final Participant caller, final Participant called)
//    {
//        final String callerHandle = caller.getHandle();
//        final String calledHandle = called.getHandle();
//        return whiteListDao.isWhiteListedNumber(callerHandle, calledHandle);
//    }
//}
