package com.dao;

import com.model.Transaction;
import com.model.User;
import java.util.List;

public interface TransactionDAO extends CrudDAO<Transaction> {
    List<Transaction> getIncomingList(User user);
    List<Transaction> getOutgoingList(User user);
}
