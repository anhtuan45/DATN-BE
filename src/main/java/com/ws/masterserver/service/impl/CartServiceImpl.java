package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.cart.request.CartRequest;
import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.dto.customer.cart.response.CountCartItem;
import com.ws.masterserver.dto.customer.cart.response.ListCartResponse;
import com.ws.masterserver.entity.CartEntity;
import com.ws.masterserver.entity.ProductOptionEntity;
import com.ws.masterserver.service.CartService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("deprecation")
public class CartServiceImpl implements CartService {

    private final WsRepository repository;

    @Override
    public ResData<String> addToCart(CurrentUser currentUser, CartRequest cartRequest) {

        AuthValidator.checkCustomerAndStaff(currentUser);

            ProductOptionEntity productOptionEntity = repository.productOptionRepository.findById(cartRequest.getProductOptionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.PRODUCT_OPTION_VI.toLowerCase(Locale.ROOT))));

            if(productOptionEntity.getQty() == 0){
                throw new WsException(WsCode.PRODUCT_OPTION_OUT_OF_STOCK);
            }

            if(productOptionEntity.getQty() < cartRequest.getQuantity()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.QTY_VI));
            }

            Optional<CartEntity> cartOptional = repository.cartRepository.findByUserIdAndAndProductOptionId(currentUser.getId(), cartRequest.getProductOptionId());
            if(cartOptional.isPresent()){

                CartEntity cart = cartOptional.get();
                int updateQuantity = cart.getQuantity() + cartRequest.getQuantity();

                cart.setQuantity(updateQuantity);

                repository.cartRepository.save(cart);

                return new ResData<>("Thêm vào giỏ hàng thành công !!!", WsCode.OK);
            }else{

                CartEntity newCart = new CartEntity();
                newCart.setId(UidUtils.generateUid());
                newCart.setUserId(currentUser.getId());
                newCart.setProductOptionId(productOptionEntity.getId());
                newCart.setQuantity(cartRequest.getQuantity());
                repository.cartRepository.save(newCart);

                return new ResData<>("Thêm vào giỏ hàng thành công !!", WsCode.OK);
            }

    }

    @Override
    public ResData<ListCartResponse> getListCart(CurrentUser currentUser) {
        AuthValidator.checkCustomerAndStaff(currentUser);
            List<CartResponse> cartList = repository.cartRepository.getListCart(currentUser.getId());

            Long totalPrice = cartList.stream().mapToLong(i -> i.getPrice() * i.getQuantity()).sum();

            Integer totalItemsInCart = repository.cartRepository.countItemInCart(currentUser.getId());

            ListCartResponse cartResponse = ListCartResponse.builder()
                    .carts(cartList)
                    .totalPrice(totalPrice)
                    .totalItems(totalItemsInCart)
                    .build();

            return new ResData<>(cartResponse,WsCode.OK);
    }

    @Override
    public ResData<String> updateCart(CurrentUser currentUser, CartRequest cartRequest) {

//        AuthValidator.checkCustomerAndStaff(currentUser);

        if (currentUser.getRole().equals(RoleEnum.ROLE_CUSTOMER) || currentUser.getRole().equals(RoleEnum.ROLE_STAFF)) {
            ProductOptionEntity productOptionEntity = repository.productOptionRepository.findById(cartRequest.getProductOptionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(WsConst.Messages.NOT_FOUND, WsConst.Nouns.PRODUCT_OPTION_VI.toLowerCase(Locale.ROOT))));

            if(productOptionEntity.getQty() < cartRequest.getQuantity()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(WsConst.Messages.INVALID, WsConst.Nouns.QTY_VI));
            }

            Optional<CartEntity> cartOptional = repository.cartRepository.findByUserIdAndAndProductOptionId(currentUser.getId(), cartRequest.getProductOptionId());

            if(cartOptional.isPresent()){
                CartEntity cart = cartOptional.get();
                int quantity = cartRequest.getQuantity();
                cart.setQuantity(quantity);
                repository.cartRepository.save(cart);
                return new ResData<>("Update quantity successfully !!!", WsCode.OK);
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, WsConst.Messages.FORBIDDEN);
    }

    @Override
    @Transactional
    public ResData<String> deleteItemInCart(CurrentUser currentUser, String productOptionId) {
        if (currentUser.getRole().equals(RoleEnum.ROLE_CUSTOMER) || currentUser.getRole().equals(RoleEnum.ROLE_STAFF)) {
            try {
                Optional<CartEntity> cartOptional = repository.cartRepository.findByUserIdAndAndProductOptionId(currentUser.getId(), productOptionId);
                if(cartOptional.isPresent()) {
                    repository.cartRepository.deleteByProductOptionId(productOptionId);
                    return new ResData<>("Đã xóa thành công !!!", WsCode.OK);
                }
            }catch (Exception e){
                log.info(" ----------------- "+e);
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, WsConst.Messages.FORBIDDEN);
    }

    @Override
    public ResData<CountCartItem> countCartItem(CurrentUser currentUser) {
        if (currentUser.getRole().equals(RoleEnum.ROLE_CUSTOMER) || currentUser.getRole().equals(RoleEnum.ROLE_STAFF)) {
            Integer itemCart = repository.cartRepository.countItemInCart(currentUser.getId());

            CountCartItem item = CountCartItem.builder()
                    .countItem(itemCart)
                    .build();

            return new ResData<>(item,WsCode.OK);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, WsConst.Messages.FORBIDDEN);
    }

}
