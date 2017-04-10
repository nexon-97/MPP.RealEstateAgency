package com.dao;

import com.services.ServiceManager;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

public class BaseDAO {
    private SessionFactory sessionFactory;

    public BaseDAO() {
        ApplicationContext context = ServiceManager.getInstance().getSharedResources().getApplicationContext();
        this.sessionFactory = context.getBean(SessionFactory.class);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
