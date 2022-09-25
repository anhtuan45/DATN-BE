package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.favourite.request.FavouriteRequest;
import com.ws.masterserver.utils.base.rest.CurrentUser;

public interface FavouriteService {
    Object getListFavourite(CurrentUser currentUser);
    Object createFavourite(CurrentUser currentUser, FavouriteRequest req);
    void deleteFavourite(CurrentUser currentUser,String id);
}
