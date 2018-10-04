package com.avaya.services.whitelist.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.avaya.collaboration.businessdata.api.NoAttributeFoundException;
import com.avaya.collaboration.businessdata.api.ServiceNotFoundException;

public interface IEntityManagerFactory
{
    EntityManagerFactory createEntityManagerFactory() throws NoAttributeFoundException,
            ServiceNotFoundException;

    void destroyEntityManagerFactory();

    EntityManager initializeEntityManager() throws NoAttributeFoundException, ServiceNotFoundException;

    EntityManagerFactory getFactory();

    void setFactory(final EntityManagerFactory factory);
}
