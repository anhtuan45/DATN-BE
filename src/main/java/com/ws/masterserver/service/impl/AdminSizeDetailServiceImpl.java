package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.size.search.SizeReq;
import com.ws.masterserver.dto.admin.size.search.SizeRes;
import com.ws.masterserver.entity.SizeEntity;
import com.ws.masterserver.service.AdminSizeDetailService;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.common.StatusResUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminSizeDetailServiceImpl implements AdminSizeDetailService {

    private final WsRepository repository;

    @Override
    public Object search(CurrentUser currentUser, SizeReq payload) {
        AuthValidator.checkAdminAndStaff(currentUser);
        log.info("search() payload: {}", JsonUtils.toJson(payload));
        PageReq pageReq = payload.getPageReq();
        PageableUtils.getPageReq(pageReq);
        String textSearch = payload.getTextSearch();
        if(StringUtils.isNullOrEmpty(textSearch)) {
            textSearch = "";
        }
        textSearch = textSearch.toUpperCase();
        Pageable pageable = PageableUtils.getPageable(pageReq);
        Page<SizeEntity> sizePage = repository.sizeRepository.search(textSearch, payload.getActive(), pageable);
        return PageData.setResult(
                sizePage.getContent().stream().map(AdminSizeDetailServiceImpl::convertEntity2Res).collect(Collectors.toList()),
                sizePage.getNumber(),
                sizePage.getSize(),
                sizePage.getTotalElements());
    }

    private static SizeRes convertEntity2Res(SizeEntity size) {
        return SizeRes.builder()
                .id(size.getId())
                .name(size.getName())
                .status(StatusResUtils.getStatus(size.getActive()))
                .build();
    }
}
