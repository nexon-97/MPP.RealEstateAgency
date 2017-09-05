package com.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;


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
