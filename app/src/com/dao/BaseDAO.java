package com.dao;

import com.services.shared.ServiceManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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

    protected Session openSession() {
        try {
            Session session = getSessionFactory().openSession();
            return session;
        } catch (HibernateException e) {
            return null;
        }
    }
}
