package com.services;

import com.model.*;

public interface PermissionService {
    boolean canEditOffer(User user, Offer offer);
    boolean canDeleteOffer(User user, Offer offer);
    boolean canDeleteProperty(User user, Property property);
    boolean canEditUserInfo(User user);
}
