package com.dao;

import com.model.DealRequest;
import com.model.User;
import java.util.List;

public interface DealRequestDAO extends CrudDAO<DealRequest> {
    List<DealRequest> listUncommittedRealtorRequests(User realtor);
    List<DealRequest> listUncommittedBuyerRequests();
    List<DealRequest> listUncommittedSellerRequests(User seller);
    boolean checkDealRequestClone(DealRequest request);
}
