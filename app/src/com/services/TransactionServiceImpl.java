package com.services;

import com.model.Transaction;
import com.dao.TransactionDAO;
import com.dao.TransactionDAOImpl;
import com.model.User;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.List;

public class TransactionServiceImpl extends BaseService implements TransactionService  {
    public TransactionServiceImpl(ServiceSharedResources sharedResources) { super(ServiceId.TransactionService, sharedResources); }

    @Override
    public boolean addTransaction(User buyer, User seller, BigDecimal companyFine, BigDecimal payment) {
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        Transaction transaction = new Transaction();
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

        transaction.setBuyer(buyer);
        transaction.setSeller(seller);
        transaction.setCompanyFine(companyFine);
        transaction.setPayment(payment);
        transaction.setDate(cal.getTime());

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
}
