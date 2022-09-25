package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.cart.request.CartRequest;
import com.ws.masterserver.dto.customer.cart.response.CountCartItem;
import com.ws.masterserver.dto.customer.cart.response.ListCartResponse;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

public interface CartService {
    ResData<String> addToCart(CurrentUser currentUser, CartRequest cart);

    ResData<ListCartResponse> getListCart(CurrentUser currentUser);

    ResData<String> updateCart(CurrentUser currentUser, CartRequest cart);

    ResData<String> deleteItemInCart(CurrentUser currentUser,String productOptionId);

    ResData<CountCartItem> countCartItem(CurrentUser currentUser);
}
