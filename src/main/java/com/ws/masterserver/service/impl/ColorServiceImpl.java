package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.color.ColorResponseV2;
import com.ws.masterserver.dto.admin.color.search.ColorReq;
import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.ColorService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.enum_dto.ColorDto;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColorServiceImpl implements ColorService {

    private final WsRepository repository;

    @Override
    @Transactional
    public ResData<String> create(CurrentUser currentUser, ColorDto dto) {
        AuthValidator.checkAdminAndStaff(currentUser);
        Optional<ProductEntity> products = repository.productRepository.findById(dto.getId());
        if (Boolean.FALSE.equals(products != null)){
            throw new WsException(WsCode.PRODUCT_NOT_FOUND);
        }
        if (Boolean.TRUE.equals(repository.colorRepository.findByName(dto.getName().trim().toLowerCase()))) {
            throw new WsException(WsCode.COLOR_EXISTS_NAME);
        }
        if (Boolean.TRUE.equals(repository.colorRepository.findByHex(dto.getHex().trim()))) {
            throw new WsException(WsCode.COLOR_EXISTS_HEX);
        }
        ColorEntity color = ColorEntity.builder()
                .id(UidUtils.generateUid())
                .name(dto.getName().trim())
                .hex(dto.getHex().trim())
                .active(Boolean.TRUE)
                .build();
        repository.colorRepository.save(color);
        log.info("create finished at {} with response: {}", new Date(), JsonUtils.toJson(color));
        return new ResData<>(color.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> delete(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        if (id == null || Boolean.FALSE.equals(repository.colorRepository.findByIdAndActive(id, Boolean.TRUE))) {
            throw new WsException(WsCode.COLOR_NOT_FOUND);
        }
        ColorEntity color = repository.colorRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        repository.colorRepository.delete(color);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(color));
        return new ResData<>(color.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> changeStatus(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        if (id == null) {
            throw new WsException(WsCode.COLOR_NOT_FOUND);
        }
        ColorEntity color = repository.colorRepository.findById(id).orElse(null);
        color.setActive(!color.getActive());
        repository.colorRepository.save(color);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(color));
        return new ResData<>(color.getId(), WsCode.OK);
    }

    @Override
    @Transactional
    public ResData<String> update(CurrentUser currentUser, ColorDto dto) {
        AuthValidator.checkAdminAndStaff(currentUser);
        if (dto.getId() == null || Boolean.FALSE.equals(repository.colorRepository.existsByIdAndActive(dto.getId(), Boolean.TRUE))) {
            throw new WsException(WsCode.COLOR_NOT_FOUND);
        }
        ColorEntity color = repository.colorRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        color.setName(dto.getName().trim());
        color.setHex(dto.getHex().trim());
        repository.colorRepository.save(color);
        log.info("update finished at {} with response: {}", new Date(), JsonUtils.toJson(color));
        return new ResData<>(color.getId(), WsCode.OK);
    }

    @Override
    public ResData<List<ColorResponse>> getListColor() {
        List<ColorResponse> color = repository.colorRepository.findAllColor();
        return new ResData<>(color, WsCode.OK);
    }

    @Override
    public ResData<List<ColorResponseV2>> getListColorV2() {
        List<ColorResponseV2> color = repository.colorRepository.findAllColorV2();
        return new ResData<>(color, WsCode.OK);
    }

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdminAndStaff(currentUser);
        ColorEntity color = repository.colorRepository.findById(id).orElseThrow(() -> {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        });
        return ColorDto.builder()
                .id(color.getId())
                .name(color.getName())
                .hex(color.getHex())
                .build();
    }

    public Object noPage() {
        return repository.colorRepository.findByActive(true);

    }

    @Override
    public Object search(CurrentUser currentUser, ColorReq payload) {
        AuthValidator.checkAdminAndStaff(currentUser);
        String textSearch;
        if (StringUtils.isNullOrEmpty(payload.getTextSearch())) {
            payload.setTextSearch("");
        }
        textSearch = "%" + payload.getTextSearch().trim().toUpperCase(Locale.ROOT) + "%";

        Pageable pageable = PageableUtils.getPageable(payload.getPageReq());
        Page<ColorEntity> colorEntityPage = repository.colorRepository.searchColor(payload.getActive(), textSearch, pageable);

        return PageData.setResult(colorEntityPage.getContent().stream().map(o -> {
                    ColorEntity colorEntity = repository.colorRepository.findById(o.getId()).orElse(null);
                    return ColorResponseV2.builder()
                            .id(o.getId())
                            .hex(o.getHex())
                            .name(o.getName())
                            .active(o.getActive())
                            .createdDate(o.getCreatedDate())
                            .build();
                }).collect(Collectors.toList()),
                colorEntityPage.getNumber(),
                colorEntityPage.getSize(),
                colorEntityPage.getTotalElements());
    }
}
