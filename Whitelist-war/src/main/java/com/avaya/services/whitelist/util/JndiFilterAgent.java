/////////////////////////////////////////////////////////////////////////////
//Copyright Avaya Inc., All Rights Reserved
// THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF AVAYA INC
// The copyright notice above does not evidence any
// actual or intended publication of such source code.
// Some third-party source code components may have been modified from
// their original versions by Avaya Inc.
// The modifications are Copyright Avaya Inc., All Rights Reserved.
//////////////////////////////////////////////////////////////////////////////
package com.avaya.services.whitelist.util;

import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;

public interface JndiFilterAgent
{
    List<JndiEntry> get(Context context, String classNameFilter) throws NamingException;
}