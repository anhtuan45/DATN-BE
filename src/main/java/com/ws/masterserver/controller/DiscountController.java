package com.ws.masterserver.controller;

import com.ws.masterserver.dto.admin.discount.search.DiscountRequest;
import com.ws.masterserver.dto.customer.discount.DiscountReq;
import com.ws.masterserver.utils.base.WsController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount")
@RequiredArgsConstructor
@Slf4j
public class DiscountController extends WsController {

    @Operation(summary = "List my discount")
    @PostMapping("/my-discount")
    public ResponseEntity<?> updateReview(@RequestBody DiscountReq payload){
        log.info("----- API /api/v1/discount/my-discount --------");
        return ResponseEntity.ok(service.discountService.getListMyDiscount(getCurrentUser(),payload));
    }

}
