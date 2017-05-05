package com.services;

import com.model.Transaction;
import com.dao.TransactionDAO;
import com.dao.TransactionDAOImpl;
import com.model.User;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class TransactionServiceImpl extends BaseService implements TransactionService  {
    public TransactionServiceImpl(ServiceSharedResources sharedResources) { super(ServiceId.TransactionService, sharedResources); }

    @Override
    public boolean addTransaction(User buyer, User seller, BigDecimal companyFine, BigDecimal payment) {
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        Transaction transaction = new Transaction();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        long now = calendar.getTimeInMillis();

        Timestamp date = new Timestamp(now);

        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setCompanyFine(companyFine);
        transaction.setPayment(payment);
        transaction.setDate(date);

        return transactionDAO.addTransaction(transaction);
    }

    @Override
    public Transaction getTransactionById(int id){
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        return transactionDAO.getById(id);
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

    @Override
    public List<Transaction> getTransactionsList(){
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        return transactionDAO.list();
    }
}
