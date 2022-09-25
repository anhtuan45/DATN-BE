package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.address.AddressReq;
import com.ws.masterserver.dto.customer.address.AddressReqNoId;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController extends WsController {

    @Operation(summary = "API get list address")
    @GetMapping("/list-address")
    public ResponseEntity<?> getListAddress(){
        log.info("------ start api get list address -----");
        return ResponseEntity.ok(service.addressService.getListAddress(getCurrentUser()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "API get address detail")
    public ResponseEntity<?> getAddressById(@PathVariable("id") String id){
        log.info("------ start api get address byId -----");
        return ResponseEntity.ok(service.addressService.getAddressById(getCurrentUser(),id));
    }

    @PostMapping("/create")
    @Operation(summary = "API create address")
    public ResponseEntity<?> createNewAddress(@RequestBody AddressReq req){
        log.info("------ start api create address -----");
        return ResponseEntity.ok(service.addressService.createAddress(getCurrentUser(),req));
    }

    @PostMapping("/create/v2")
    @Operation(summary = "API create address")
    public ResponseEntity<?> createNewAddressv2(@RequestBody AddressReqNoId req){
        log.info("------ start api create address V2 -----");
        return ResponseEntity.ok(service.addressService.createAddressV2(getCurrentUser(),req));
    }

    @PostMapping("/update")
    @Operation(summary = "API update address")
    public ResponseEntity<?> updateAddress(@RequestBody AddressReq req){
        log.info("------ start api update address -----");
        return ResponseEntity.ok(service.addressService.updateAddress(getCurrentUser(),req));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "API delete address")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") String id){
        log.info("------ start api delete address -----");
        service.addressService.deleteAddress(getCurrentUser(),id);
        return ResponseEntity.ok("Delete address successfully !!");
    }

    @GetMapping("/default/{id}")
    @Operation(summary = "API setDefault address")
    public ResponseEntity<?> setAddressDefault(@PathVariable("id") String id) {
        service.addressService.setAddressDefault(getCurrentUser(),id);
        return ResponseEntity.ok("Set default address successfully !");
    }

    @GetMapping("/default")
    @Operation(summary = "Get Address Default")
    public ResponseEntity<?> getAddressDefault() {
        log.info("------ Get Address Default -----");
        return ResponseEntity.ok(service.addressService.getAddressDefault(getCurrentUser()));
    }


}
