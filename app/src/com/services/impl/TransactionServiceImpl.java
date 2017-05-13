package com.services.impl;

import com.model.Transaction;
import com.dao.TransactionDAO;
import com.dao.impl.TransactionDAOImpl;
import com.model.User;
import com.services.TransactionService;
import com.services.shared.AbstractCrudService;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class TransactionServiceImpl extends AbstractCrudService<Transaction> implements TransactionService {

    public TransactionServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.TransactionService, sharedResources, Transaction.class);
    }

    @Override
    public List<Transaction> getIncomingTransactions(User user){
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        return transactionDAO.getIncomingList(user);
    }

    @Override
    public List<Transaction> getOutgoingTransactions(User user){
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        return transactionDAO.getOutgoingList(user);
    }
}
