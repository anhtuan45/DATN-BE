package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.favourite.request.FavouriteRequest;
import com.ws.masterserver.dto.customer.favourite.response.FavouriteResponse;
import com.ws.masterserver.entity.FavouriteEntity;
import com.ws.masterserver.service.FavouriteService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavouriteServiceImpl implements FavouriteService {
    private final WsRepository repository;

    @Override
    public Object getListFavourite(CurrentUser currentUser) {

        List<FavouriteResponse> res = repository.favouriteRepository.getListFavourites(currentUser.getId());

        return new ResData<>(res,WsCode.OK);
    }

    @Override
    public Object createFavourite(CurrentUser currentUser, FavouriteRequest req) {
        log.info("----- start create favourite -----");
        com.ws.masterserver.entity.ProductEntity product = repository.productRepository.findById(req.getProductId()).orElse(null);
        if (product == null) {
            throw new WsException(WsCode.PRODUCT_NOT_FOUND);
        }

        if(!repository.favouriteRepository.existsByProductId(product.getId())){
            FavouriteEntity favourite = FavouriteEntity.builder()
                    .id(UidUtils.generateUid())
                    .productId(product.getId())
                    .userId(currentUser.getId())
                    .build();
            repository.favouriteRepository.save(favourite);

            return new ResData<>(Boolean.TRUE,WsCode.OK);
        }else{
            FavouriteEntity favourite = repository.favouriteRepository.findByProductId(product.getId());
            repository.favouriteRepository.deleteById(favourite.getId());
            return new ResData<>(Boolean.FALSE,WsCode.OK);
        }

    }

    @Override
    public void deleteFavourite(CurrentUser currentUser, String id) {
        log.info("----- start delete favourite -----");
        FavouriteEntity favourite = repository.favouriteRepository.findById(id).orElse(null);

        if (favourite == null) {
            throw new WsException(WsCode.FAVOURITE_NOT_FOUND);
        }
        repository.favouriteRepository.deleteById(id);
    }
}
