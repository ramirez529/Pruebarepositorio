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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.call.Call;
import com.avaya.collaboration.call.Participant;
import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.util.EjbJndiLookup;
import com.avaya.services.whitelist.util.EntityManagerFactoryImpl;
import com.avaya.services.whitelist.util.IEntityManagerFactory;
import com.avaya.zephyr.platform.dal.api.ServiceDescriptor;

public final class WhiteListTest
{

    private WhiteList whiteList;
    private ServiceDescriptor serviceDescriptor;
    private DestinationFinder destinationFinder;
    private EjbJndiLookup ejbJndiLookup;
    private Logger logger;

    private Call call;
    private Participant caller;
    private Participant called;

    private ServiceAttributes serviceAttributes;
    private IEntityManagerFactory entityManagerFactory;
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    @Before
    public void setUp() throws NoAttributeFoundException, ServiceNotFoundException
    {
        serviceDescriptor = mock(ServiceDescriptor.class);
        destinationFinder = mock(DestinationFinder.class);
        ejbJndiLookup = mock(EjbJndiLookup.class);
        logger = mock(Logger.class);

        call = mock(Call.class);
        caller = mock(Participant.class);
        called = mock(Participant.class);

        entityManagerFactory = EntityManagerFactoryImpl.getInstance();
        factory = mock(EntityManagerFactory.class);
        entityManagerFactory.setFactory(factory);
        entityManager = mock(EntityManager.class);
        when(entityManagerFactory.initializeEntityManager()).thenReturn(entityManager);

        serviceAttributes = new ServiceAttributes();
        serviceAttributes.init();
    }

    @Test
    public void callIntercepted() throws Exception
    {
        whiteList = new WhiteList(serviceDescriptor, ejbJndiLookup, logger);

        when(ejbJndiLookup.get(anyString(), anyString(), anyString()))
                .thenReturn(destinationFinder);

        whiteList.callIntercepted(call);

        verify(call).divertTo(destinationFinder.getDestination(caller, called));
    }

    @Test
    public void callIntercepted_null_destinationFinder()
    {
        whiteList = new WhiteList(serviceDescriptor, ejbJndiLookup, logger);

        whiteList.callIntercepted(call);

        verify(call).allow();
    }

    @Test
    public void callIntercepted_destinationFinder_redirectCall_exception()
    {
        whiteList = new WhiteList(serviceDescriptor, ejbJndiLookup, logger);

        doThrow(new IllegalStateException()).when(call).divertTo(Matchers.anyString());

        whiteList.callIntercepted(call);

        verify(call).allow();
    }
}
