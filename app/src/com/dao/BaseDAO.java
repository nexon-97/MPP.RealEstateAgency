package com.dao;

import com.services.shared.ServiceManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

public class BaseDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public BaseDAO() {
        ApplicationContext context = ServiceManager.getInstance().getSharedResources().getApplicationContext();
        this.sessionFactory = context.getBean(SessionFactory.class);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected Session getSession() {
        if (this.session != null) {
            return this.session;
        } else {
            try {
                this.session = getSessionFactory().openSession();
            } catch (HibernateException e) {
                return null;
            }
        }

        return this.session;
    }

    protected void closeSession() {
        if (this.session != null) {
            this.session.close();
            this.session = null;
        }
    }
}
