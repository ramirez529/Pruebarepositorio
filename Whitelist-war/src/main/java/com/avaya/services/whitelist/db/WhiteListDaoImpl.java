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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;
import com.avaya.collaboration.util.logger.Logger;
import com.avaya.services.whitelist.util.EntityManagerFactoryImpl;

//public class WhiteListDaoImpl implements WhiteListDao
//{
//    private EntityManager entityManager = null;
//
//    private final Logger logger;
//
//    public WhiteListDaoImpl()
//    {
//        this.logger = Logger.getLogger(WhiteListDaoImpl.class);
//    }
//
//    private void initializeEntityManager()
//    {
//        try
//        {
//            logger.finer("initializeEntityManager ENTER");
//            entityManager = EntityManagerFactoryImpl.getInstance()
//                    .initializeEntityManager();
//        }
//        catch (final NoAttributeFoundException | ServiceNotFoundException e)
//        {
//            entityManager = null;
//            logger.error("initializeEntityManager exception=", e);
//        }
//        catch (final Exception e)
//        {
//            entityManager = null;
//            logger.error("initializeEntityManager exception=", e);
//            logger.logEvent("DB_ERROR_01");
//        }
//    }
//
//    WhiteListDaoImpl(final EntityManager entityManager, final Logger logger)
//    {
//        this.entityManager = entityManager;
//        this.logger = logger;
//    }
//
//    @Override
//    public final String isWhiteListedNumber(membernum)
//    {
//        logEnter(membernum);
//
//        initializeEntityManager();
//
//        try
//        {
//            if (entityManager != null)
//            {
//                final Query query = createNamedQuery(membernum);
//                final WhiteListEntry whiteListEntry = (WhiteListEntry) getSingleResult(query);
//                logger.info("isWhiteListedNumber from named query whiteListEntry="
//                        + whiteListEntry);
//                return (whiteListEntry != null);
//            }
//            else
//            {
//                return false;
//            }
//        }
//        catch (final Exception exception)
//        {
//            logger.error("isWhiteListedNumber exception=", exception);
//            return false;
//        }
//        finally
//        {
//            if (entityManager != null)
//            {
//                entityManager.close();
//                entityManager = null;
//            }
//        }
//    }
//
//    private Query createNamedQuery(final String callingNumber,
//            final String calledNumber)
//    {
//        return entityManager
//                .createNamedQuery(NamedQueries.FIND_WHITELIST_ENTRY)
//                .setParameter("calledNumber", calledNumber)
//                .setParameter("callingNumber", callingNumber);
//    }
//
//    private void logEnter(final String callingNumber, final String calledNumber)
//    {
//        if (logger.isFinerEnabled())
//        {
//            final StringBuilder enterLog = new StringBuilder(
//                    "isWhiteListedNumber ENTER entityManager=");
//            enterLog.append(entityManager);
//            enterLog.append(" caller number=" + callingNumber);
//            enterLog.append(" called number=" + calledNumber);
//            logger.finer(enterLog);
//        }
//    }
//
//    private Object getSingleResult(final Query query)
//    {
//        try
//        {
//            return query.getSingleResult();
//        }
//        catch (final NoResultException noResultException)
//        {
//            return null;
//        }
//        catch (final Exception exception)
//        {
//            logger.error("getSingleResult exception=" + exception, exception);
//            return null;
//        }
//    }
//}