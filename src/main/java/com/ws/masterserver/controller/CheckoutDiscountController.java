package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.order.checkout.CheckoutDiscountReq;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CheckoutDiscountController extends WsController {

    @Operation(summary = "api check ma km khi checkout")
    @PostMapping("/v1/discount/checkout")
    public Object checkoutWithDiscount(@RequestBody CheckoutDiscountReq payload) {
        log.info("START API /api/v1/discount/checkout with payload: {}", JsonUtils.toJson(payload));
        return service.checkoutDiscountService.check(getCurrentUser(), payload);
    }

    @Operation(summary = "api check ma km khi checkout")
    @PostMapping("/v2/checkout/verify-discount")
    public Object checkoutWithDiscountV2(@RequestBody CheckoutDiscountReq payload) {
        log.info("START API /api/v1/discount/checkout with payload: {}", JsonUtils.toJson(payload));
        return service.checkoutDiscountServiceV2.verifyDiscountWhenCheckout(getCurrentUser(), payload);
    }


}
