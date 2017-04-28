package com.dao;

import com.model.Deal;

public interface DealDAO {
    boolean addDeal(Deal deal);
    Deal getDealById(int id);
}
