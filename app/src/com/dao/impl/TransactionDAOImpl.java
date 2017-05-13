package com.dao.impl;

import com.dao.AbstractCrudDAO;
import com.dao.TransactionDAO;
import com.model.Transaction;
import com.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class TransactionDAOImpl extends AbstractCrudDAO<Transaction> implements TransactionDAO {

    public TransactionDAOImpl() {
        super(Transaction.class);
    }

    @Override
    public List<Transaction> getIncomingList(User user) {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Transaction.class)
                    .add(Restrictions.eq("seller", user));

            return super.filter(criteria);
        }

        return null;
    }

    @Override
    public List<Transaction> getOutgoingList(User user) {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Transaction.class)
                    .add(Restrictions.eq("buyer", user));

            return super.filter(criteria);

        }

        return null;
    }
}
