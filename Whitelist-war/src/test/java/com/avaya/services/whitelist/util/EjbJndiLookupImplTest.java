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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.SizeLimitExceededException;

import org.junit.Before;
import org.junit.Test;

import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.DestinationFinder;
import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;

public class EjbJndiLookupImplTest
{
    private EjbJndiLookup ejbJndiLookup;
    private JndiFilterAgent jndiFilterAgent;
    private InitialContext initialContext;
    private Logger logger;

    private DestinationFinder destinationFinder;
    
    final String serviceName = "WhiteList";
    final String serviceVersion = "2.0.0.0.0";
    final String sessionInterface = "com.avaya.services.whitelist.DestinationFinder";
    
    final String contextRoot = "ejblocal:" + serviceName + '-' + serviceVersion;
    final List<JndiEntry> jndiEntries = new ArrayList<JndiEntry>();
    final JndiEntry jndiEntry1 = new JndiEntry("context1", "key1", "value1");
    final JndiEntry jndiEntry2 = new JndiEntry("context2", "key2", "value2");
    
    @Before
    public void setUp() throws Exception
    {
        jndiFilterAgent = mock(JndiFilterAgent.class);
        initialContext = mock(InitialContext.class);
        logger = mock(Logger.class);

        destinationFinder = mock(DestinationFinder.class);
        
        ejbJndiLookup = new EjbJndiLookupImpl(jndiFilterAgent, initialContext, logger);
    }

    @Test
    public void get() throws Exception
    {
        jndiEntries.add(jndiEntry1);
        
        when(initialContext.lookup(contextRoot)).thenReturn(initialContext);
        when(initialContext.lookup(jndiEntry1.getKey() +'/' + jndiEntry1.getValue())).thenReturn(destinationFinder);
        when(jndiFilterAgent.get(initialContext, sessionInterface)).thenReturn(jndiEntries);
        
    }
    
    @Test(expected=NoAttributeFoundException.class)
    public void get_noJndiObject() throws Exception
    {
        when(initialContext.lookup(contextRoot)).thenReturn(initialContext);
        when(jndiFilterAgent.get(initialContext, sessionInterface)).thenReturn(new ArrayList<JndiEntry>());
        
        assertEquals(destinationFinder, ejbJndiLookup.get(serviceName, serviceVersion, sessionInterface));
    }
    
    @Test(expected=SizeLimitExceededException.class)
    public void get_tooManyResults() throws Exception
    {
        jndiEntries.add(jndiEntry1);
        jndiEntries.add(jndiEntry2);
        
        when(initialContext.lookup(contextRoot)).thenReturn(initialContext);
        when(jndiFilterAgent.get(initialContext, sessionInterface)).thenReturn(jndiEntries);
        
        assertEquals(destinationFinder, ejbJndiLookup.get(serviceName, serviceVersion, sessionInterface));
    }
}
