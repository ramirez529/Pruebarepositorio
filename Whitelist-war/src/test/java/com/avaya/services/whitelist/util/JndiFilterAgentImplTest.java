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
package com.avaya.services.whitelist.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;

import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.AlternateDestinationFinder;
import com.avaya.services.whitelist.DestinationFinder;
import com.avaya.services.whitelist.DestinationFinderImpl;
import com.avaya.services.whitelist.PermissionAgent;
import com.google.common.base.Splitter;

public class JndiFilterAgentImplTest
{
    private JndiFilterAgent jndiFilterAgent;
    private Logger logger;
    
    private InitialContext initialContext;
    
    @Before
    public void setUp() throws Exception
    {
        logger = mock(Logger.class);

        jndiFilterAgent = new JndiFilterAgentImpl(logger);

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        initialContext = new InitialContext();
    }

    void createJndiContext(final String subContext) throws Exception
    {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        final InitialContext initialContext = new InitialContext();

        initialContext.createSubcontext("ejblocal:");

        Iterable<String> subContextIterator = Splitter.on('/').split(subContext);
        StringBuilder subContexts = new StringBuilder("ejblocal:");
        for (String context : subContextIterator)
        {
            subContexts.append('/' + context);
            System.out.println("subContexts=" + subContexts.toString());
            initialContext.createSubcontext(subContexts.toString());
        }

        // initialContext.createSubcontext("ejblocal:/WhiteList-2.0.0.0");
        // initialContext.createSubcontext("ejblocal:/" + subContext);
    }

    void bindJndiKeyValue(final String resource, final Object object) throws Exception
    {
        System.out.println("bindJndiObject =" + "ejblocal:/" + resource + " ==> " + object);
        initialContext.bind("ejblocal:/" + resource, object);
     }
    
    void bindJndiKeyObject(final String resource, final Object object) throws Exception
    {
        System.out.println("bindJndiProperty =" + "ejblocal:/" + resource + " ==> " + object);
        initialContext.addToEnvironment("ejblocal:/" + resource, object);
    }

    @Test
    public void get() throws Exception
    {
        final String serviceName = "WhiteList";
        final String serviceVersion = "2.0.0.0";
        final String sessionInterface = "com.avaya.services.whitelist.DestinationFinder";
        final String sessionEjb = "DestinationFinderImpl#com.avaya.services.whitelist.DestinationFinder";
        
        final String subContext =
                serviceName + '-' + serviceVersion + '/' + "Whitelist-war-2.0.0.0.0-SNAPSHOT.war";

        final PermissionAgent permissionAgent = mock(PermissionAgent.class);
        final AlternateDestinationFinder alternateDestinationFinder = mock(AlternateDestinationFinder.class);
        final DestinationFinder destinationFinder = new DestinationFinderImpl(permissionAgent, alternateDestinationFinder, logger);
        
        createJndiContext(subContext);
        bindJndiKeyValue(subContext + '/' + sessionEjb, destinationFinder);
        bindJndiKeyObject(subContext + '/' + sessionEjb, destinationFinder);

        List<JndiEntry> expectedJndiEntries = new ArrayList<JndiEntry>();
        expectedJndiEntries.add(new JndiEntry(null, destinationFinder.getClass().getName(), sessionEjb));
        
        final List<JndiEntry> actualJndiEntries = jndiFilterAgent.get(initialContext, sessionInterface);
        
        assertEquals(expectedJndiEntries, actualJndiEntries);
    }
}
