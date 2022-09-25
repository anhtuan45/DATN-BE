package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.address.AddressReq;
import com.ws.masterserver.dto.customer.address.AddressReqNoId;
import com.ws.masterserver.dto.customer.address.AddressRes;
import com.ws.masterserver.entity.AddressEntity;
import com.ws.masterserver.service.AddressService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.customer.address.AddressValidator;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("deprecation")
public class AddressServiceImpl implements AddressService {

    private final WsRepository repository;

    @Override
    public Object getListAddress(CurrentUser currentUser) {
        List<AddressRes> res = repository.addressRepository.getListAddressByUserId(currentUser.getId());
        return new ResData<>(res, WsCode.OK);
    }

    @Override
    public Object createAddress(CurrentUser currentUser, AddressReq req) {
        log.info("----- Address create start ------");
        AuthValidator.checkCustomerAndStaff(currentUser);
        try {
            List<AddressRes> res = repository.addressRepository.getListAddressByUserId(currentUser.getId());
            if(res.isEmpty()){
                AddressValidator.validCreateAddress(req);
                AddressEntity address = AddressEntity.builder()
                        .id(UidUtils.generateUid())
                        .userId(currentUser.getId())
                        .nameOfRecipient(req.getNameOfRecipient().trim())
                        .phoneNumber(req.getPhoneNumber().trim())
                        .exact(req.getExact().trim())
                        .wardCode(req.getWardCode().trim())
                        .wardName(req.getWardName().trim())
                        .districtId(req.getDistrictId().trim())
                        .districtName(req.getDistrictName().trim())
                        .provinceId(req.getProvinceId().trim())
                        .provinceName(req.getProvinceName().trim())
                        .isDefault(Boolean.TRUE)
                        .active(Boolean.TRUE)
                        .combination(req.getExact().trim().concat(", ").concat(req.getWardName().trim()).concat(", ").concat(req.getDistrictName().trim()).concat(", ").concat(req.getProvinceName().trim()))
                        .createdBy(currentUser.getId())
                        .createdDate(new Date())
                        .build();
                repository.addressRepository.save(address);
                return new ResData<>(address.getId(),WsCode.CREATED);
            }else {
                AddressEntity address = AddressEntity.builder()
                        .id(UidUtils.generateUid())
                        .userId(currentUser.getId())
                        .nameOfRecipient(req.getNameOfRecipient().trim())
                        .phoneNumber(req.getPhoneNumber().trim())
                        .exact(req.getExact().trim())
                        .wardCode(req.getWardCode().trim())
                        .wardName(req.getWardName().trim())
                        .districtId(req.getDistrictId().trim())
                        .districtName(req.getDistrictName().trim())
                        .provinceId(req.getProvinceId().trim())
                        .provinceName(req.getProvinceName().trim())
                        .isDefault(Boolean.FALSE)
                        .active(Boolean.TRUE)
                        .combination(req.getExact().trim().concat(", ").concat(req.getWardName().trim()).concat(", ").concat(req.getDistrictName().trim()).concat(", ").concat(req.getProvinceName().trim()))
                        .createdBy(currentUser.getId())
                        .createdDate(new Date())
                        .build();
                repository.addressRepository.save(address);
                return new ResData<>(address.getId(), WsCode.CREATED);
            }
        }catch (Exception e) {
            log.error("----- Address create error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    @Override
    public Object updateAddress(CurrentUser currentUser, AddressReq req) {
        log.info("----- Address update start ------");
        AuthValidator.checkCustomerAndStaff(currentUser);

        AddressEntity address = repository.addressRepository.findById(req.getId()).orElseThrow(() -> new WsException(WsCode.ADDRESS_NOT_FOUND));

        try {
            address.setExact(req.getExact().trim());
            address.setProvinceId(req.getProvinceId().trim());
            address.setProvinceName(req.getProvinceName().trim());
            address.setDistrictId(req.getDistrictId().trim());
            address.setDistrictName(req.getDistrictName().trim());
            address.setWardCode(req.getWardCode().trim());
            address.setWardName(req.getWardName().trim());
            address.setNameOfRecipient(req.getNameOfRecipient().trim());
            address.setPhoneNumber(req.getPhoneNumber().trim());
            address.setUpdatedBy(currentUser.getId());
            address.setUpdatedDate(new Date());
            address.setCombination(req.getExact().trim().concat(", ").concat(req.getWardName().trim()).concat(", ").concat(req.getDistrictName().trim()).concat(", ").concat(req.getProvinceName().trim()));

            repository.addressRepository.save(address);

            return new ResData<>(address.getId(), WsCode.OK);
        } catch (Exception e) {
            log.error("----- Address update error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }

    }

    @Override
    public void deleteAddress(CurrentUser currentUser, String id) {
        log.info("----- Address delete start ------");
        AuthValidator.checkCustomerAndStaff(currentUser);
        AddressEntity address = repository.addressRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ADDRESS_NOT_FOUND));

        repository.addressRepository.deleteById(id);
    }

    @Override
    public void setAddressDefault(CurrentUser currentUser, String id) {
        log.info("----- Address set default start ------");
        AuthValidator.checkCustomerAndStaff(currentUser);
        List<AddressEntity> listAddress = repository.addressRepository.getAddressByUserId(currentUser.getId());
        listAddress.forEach(address -> {
            address.setIsDefault(Boolean.FALSE);

            if(address.getId().equals(id)) {
                address.setIsDefault(Boolean.TRUE);
            }
        });
        repository.addressRepository.saveAll(listAddress);


    }

    @Override
    public Object getAddressById(CurrentUser currentUser, String id) {
        log.info("----- Address get ById start ------");
        AuthValidator.checkCustomerAndStaff(currentUser);

        AddressEntity address = repository.addressRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ADDRESS_NOT_FOUND));

        AddressRes res = AddressRes.builder()
                .id(address.getId())
                .nameOfRecipient(address.getNameOfRecipient())
                .exact(address.getExact())
                .phoneNumber(address.getPhoneNumber())
                .provinceId(address.getProvinceId())
                .provinceName(address.getProvinceName())
                .districtId(address.getDistrictId())
                .districtName(address.getDistrictName())
                .wardCode(address.getWardCode())
                .wardName(address.getWardName())
                .isDefault(address.getIsDefault())
                .active(address.getActive())
                .userId(address.getUserId())
                .combination(address.getCombination())
                .build();
        return new ResData<>(res, WsCode.OK);
    }

    @Override
    public Object getAddressDefault(CurrentUser currentUser) {
        AddressEntity address = repository.addressRepository.findAddressIsDefault(currentUser.getId());
        if(address == null){
            throw new WsException(WsCode.ADDRESS_NOT_FOUND);
        }
        return new ResData<>(address.getId(), WsCode.OK);
    }

    @Override
    public Object createAddressV2(CurrentUser currentUser, AddressReqNoId req) {
        log.info("----- Address create start V2 ------");
        AuthValidator.checkCustomerAndStaff(currentUser);
        try {
            List<AddressRes> res = repository.addressRepository.getListAddressByUserId(currentUser.getId());
            if(res.isEmpty()){
                AddressEntity address = AddressEntity.builder()
                        .id(UidUtils.generateUid())
                        .userId(currentUser.getId())
                        .nameOfRecipient(req.getNameOfRecipient().trim())
                        .phoneNumber(req.getPhoneNumber().trim())
                        .exact(req.getExact().trim())
                        .wardCode(req.getWardCode().trim())
                        .wardName(req.getWardName().trim())
                        .districtId(req.getDistrictId().trim())
                        .districtName(req.getDistrictName().trim())
                        .provinceId(req.getProvinceId().trim())
                        .provinceName(req.getProvinceName().trim())
                        .isDefault(Boolean.TRUE)
                        .active(Boolean.TRUE)
                        .combination(req.getExact().trim().concat(", ").concat(req.getWardName().trim()).concat(", ").concat(req.getDistrictName().trim()).concat(", ").concat(req.getProvinceName().trim()))
                        .createdBy(currentUser.getId())
                        .createdDate(new Date())
                        .build();
                repository.addressRepository.save(address);
                return new ResData<>(address.getId(),WsCode.CREATED);
            }else {
                AddressEntity address = AddressEntity.builder()
                        .id(UidUtils.generateUid())
                        .userId(currentUser.getId())
                        .nameOfRecipient(req.getNameOfRecipient().trim())
                        .phoneNumber(req.getPhoneNumber().trim())
                        .exact(req.getExact().trim())
                        .wardCode(req.getWardCode().trim())
                        .wardName(req.getWardName().trim())
                        .districtId(req.getDistrictId().trim())
                        .districtName(req.getDistrictName().trim())
                        .provinceId(req.getProvinceId().trim())
                        .provinceName(req.getProvinceName().trim())
                        .isDefault(Boolean.FALSE)
                        .active(Boolean.TRUE)
                        .combination(req.getExact().trim().concat(", ").concat(req.getWardName().trim()).concat(", ").concat(req.getDistrictName().trim()).concat(", ").concat(req.getProvinceName().trim()))
                        .createdBy(currentUser.getId())
                        .createdDate(new Date())
                        .build();
                repository.addressRepository.save(address);
                return new ResData<>(address.getId(), WsCode.CREATED);
            }
        }catch (Exception e) {
            log.error("----- Address create error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }
}
