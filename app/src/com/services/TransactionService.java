package com.services;

import com.model.Transaction;
import com.model.User;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService extends CrudService<Transaction> {
    List<Transaction> getOutgoingTransactions(User user);
    List<Transaction> getIncomingTransactions(User user);
}
