package com.services;

import com.model.DealRequest;
import com.model.User;

import java.util.List;

public interface DealRequestService extends CrudService<DealRequest> {
    public List<DealRequest> listUncommittedRealtorRequests(User realtor);
    public List<DealRequest> listUncommittedBuyerRequests();
    public List<DealRequest> listUncommittedSellerRequests(User seller);
    boolean isAlreadyRegistered(DealRequest request);
}
