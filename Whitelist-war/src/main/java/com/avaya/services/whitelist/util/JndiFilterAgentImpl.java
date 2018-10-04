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

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.avaya.collaboration.util.logger.Logger;

public final class JndiFilterAgentImpl implements JndiFilterAgent
{
    private final Logger logger;

    public JndiFilterAgentImpl()
    {
        this(Logger.getLogger(JndiFilterAgentImpl.class));
    }

    JndiFilterAgentImpl(final Logger logger)
    {
        this.logger = logger;
    }

    @Override
    public List<JndiEntry> get(final Context context, final String classNameFilter) throws NamingException
    {
        return getJndiString(context, classNameFilter, new ArrayList<JndiEntry>(), " ");
    }

    private List<JndiEntry> getJndiString(final Context context, final String classNameFilter,
            final List<JndiEntry> jndiEntries, final String indent)
            throws NamingException
    {
        final NamingEnumeration<Binding> bindings = context.listBindings("");
        while (bindings.hasMore())
        {
            final Binding item = (Binding) bindings.next();
            final String className = item.getClassName();
            final String name = item.getName();

            String nameInNameSpace;
            try
            {
                nameInNameSpace = context.getNameInNamespace();
            }
            catch (final Exception exception)
            {
                nameInNameSpace = null;
            }

            logger.fine("getJndiString [" + nameInNameSpace + "]" + indent + className + "\t" + name);

            if (className.contains(classNameFilter))
            {
                jndiEntries.add(new JndiEntry(nameInNameSpace, className, name));
            }

            final Object object = item.getObject();
            if (object instanceof Context)
            {
                getJndiString((Context) object, classNameFilter, jndiEntries, indent + " ");
            }
        }

        return jndiEntries;
    }

}
