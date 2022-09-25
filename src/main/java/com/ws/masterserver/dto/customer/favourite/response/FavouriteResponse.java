package com.ws.masterserver.dto.customer.favourite.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface FavouriteResponse {

    String getProductId();

    String getProductName();

    String getFavouriteId();

    Long getPrice();

    String getImage() ;
}
