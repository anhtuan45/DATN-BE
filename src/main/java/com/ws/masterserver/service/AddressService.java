package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.address.AddressReq;
import com.ws.masterserver.dto.customer.address.AddressReqNoId;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface AddressService {
    Object getListAddress(CurrentUser currentUser);
    Object createAddress(CurrentUser currentUser, AddressReq req);
    Object updateAddress(CurrentUser currentUser, AddressReq req);
    void deleteAddress(CurrentUser currentUser,String id);
    void setAddressDefault(CurrentUser currentUser,String id);
    Object getAddressById(CurrentUser currentUser,String id);
    Object getAddressDefault(CurrentUser currentUser);

    Object createAddressV2(CurrentUser currentUser, AddressReqNoId req);
}
