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
package com.avaya.services.whitelist.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.db.NamedQueries;
import com.avaya.services.whitelist.util.EntityManagerFactoryImpl;
import com.avaya.services.whitelist.util.IEntityManagerFactory;

//public final class WhiteListDaoImplTest
//{
//    private WhiteListDao whiteListDao;
//    private IEntityManagerFactory entityManagerFactory;
//    private EntityManagerFactory factory;
//    private EntityManager entityManager;
//    private Logger logger;
//
//    private Query query;
//    private WhiteListEntry whiteListEntry;
//
//    private final static String CALLING_NUMBER = "+15055551212";
//    private final static String CALLED_NUMBER = "+17205551212";
//
//    @Before
//    public void setUp() throws NoAttributeFoundException, ServiceNotFoundException
//    {
//        entityManagerFactory = EntityManagerFactoryImpl.getInstance();
//        factory = mock(EntityManagerFactory.class);
//        entityManagerFactory.setFactory(factory);
//        entityManager = mock(EntityManager.class);
//        logger = mock(Logger.class);
//
//        query = mock(Query.class);
//        whiteListEntry = new WhiteListEntry();
//
//        whiteListDao = new WhiteListDaoImpl(entityManager, logger);
//
//        when(entityManagerFactory.initializeEntityManager()).thenReturn(entityManager);
//
//        when(logger.isFinerEnabled()).thenReturn(true);
//    }
//
//    @Test
//    public void isWhiteListedNumber_inDb()
//    {
//        when(entityManager.createNamedQuery(NamedQueries.FIND_WHITELIST_ENTRY))
//                .thenReturn(query);
//        when(query.setParameter(anyString(), anyString())).thenReturn(query);
//        when(query.getSingleResult()).thenReturn(whiteListEntry);
//
//        assertTrue(whiteListDao.isWhiteListedNumber(CALLING_NUMBER,
//                CALLED_NUMBER));
//    }
//
//    @Test
//    public void isWhiteListedNumber_notInDb()
//    {
//        when(entityManager.createNamedQuery(NamedQueries.FIND_WHITELIST_ENTRY))
//                .thenReturn(query);
//        when(query.setParameter(anyString(), anyString())).thenReturn(query);
//        when(query.getSingleResult()).thenThrow(new NoResultException());
//
//        assertFalse(whiteListDao.isWhiteListedNumber(CALLING_NUMBER,
//                CALLED_NUMBER));
//    }
//
//    @Test
//    public void isWhiteListedNumber_invalidQuery()
//    {
//        when(entityManager.createNamedQuery(NamedQueries.FIND_WHITELIST_ENTRY))
//                .thenThrow(new IllegalArgumentException());
//
//        assertFalse(whiteListDao.isWhiteListedNumber(CALLING_NUMBER,
//                CALLED_NUMBER));
//    }
//}
