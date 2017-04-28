package com.dao;

import com.model.Deal;

import java.util.List;

public interface DealDAO {
    boolean addDeal(Deal deal);
    Deal getDealById(int id);
    List<Deal> list();
}
