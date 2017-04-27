package com.services;

import com.model.Transaction;
import com.model.User;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    boolean addTransaction(User buyer, User seller, BigDecimal companyFine, BigDecimal payment);
    Transaction getTransactionById(int id);
    List<Transaction> getOutgoingTransactions(User user);
    List<Transaction> getIncomingTransactions(User user);
}
