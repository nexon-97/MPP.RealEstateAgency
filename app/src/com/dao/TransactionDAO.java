package com.dao;

import com.model.Transaction;
import com.model.User;
import java.util.List;

public interface TransactionDAO {
    Transaction getById(int id);
    List<Transaction> getIncomingList(User user);
    List<Transaction> getOutgoingList(User user);
    boolean addTransaction(Transaction transaction);
    List<Transaction> list();
}
