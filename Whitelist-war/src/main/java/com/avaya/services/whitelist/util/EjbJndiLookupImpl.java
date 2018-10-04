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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.util.logger.Logger;
 

public final class EjbJndiLookupImpl implements EjbJndiLookup
{
    private final JndiFilterAgent jndiFilterAgent;
    private InitialContext initialContext;
    private final Logger logger;

    public EjbJndiLookupImpl() throws NamingException
    {
        this(new JndiFilterAgentImpl(), new InitialContext(), Logger.getLogger(EjbJndiLookupImpl.class));
    }

    EjbJndiLookupImpl(final JndiFilterAgent jndiFilterAgent, final InitialContext initialContext,
            final Logger logger)
    {
        this.jndiFilterAgent = jndiFilterAgent;
        this.initialContext = initialContext;
        this.logger = logger;
    }

    @Override
    public Object get(final String serviceName, final String serviceVersion, final String filter)
            throws NoAttributeFoundException, SizeLimitExceededException, NamingException
    {
        final String jndiString = getJndiString(serviceName, serviceVersion, filter);
        logger.fine("get jndiString=" + jndiString);

        return initialContext.lookup(jndiString);
    }

    private String getJndiString(final String serviceName, final String serviceVersion, final String filter)
            throws NoAttributeFoundException, SizeLimitExceededException, NamingException
    {
        final String contextRoot = "ejblocal:" + serviceName + '-' + serviceVersion;

        final Context context = (Context) initialContext.lookup(contextRoot);
        final List<JndiEntry> jndiEntries = jndiFilterAgent.get(context, filter);

        validateList(jndiEntries, filter, contextRoot);

        final JndiEntry jndiEntry = jndiEntries.get(0);
        return jndiEntry.getContext() + '/' + jndiEntry.getValue();
    }

    private void validateList(final List<JndiEntry> jndiEntries, final String filter, final String context)
            throws NoAttributeFoundException, SizeLimitExceededException
    {
        if (jndiEntries.isEmpty())
        {
            throw new NoAttributeFoundException("No interface: " + filter + " in context=" + context);
        }

        if (jndiEntries.size() > 1)
        {
            throw new SizeLimitExceededException("Multiple interfaces: " + filter + " in context=" + context);
        }
    }
}
