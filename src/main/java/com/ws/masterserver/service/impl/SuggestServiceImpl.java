package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.suggest.CategoryDto;
import com.ws.masterserver.dto.customer.suggest.SuggestDto;
import com.ws.masterserver.entity.SuggestEntity;
import com.ws.masterserver.service.SuggestService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.customer.suggest.SuggestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuggestServiceImpl implements SuggestService {

    private final WsRepository repository;

    @Override
    public List<CategoryDto> getCategories() {
        return repository.categoryRepository.findSuggestCategories();
    }

    @Override
    public Object getSizeAvailable(SuggestDto dto) {
        log.info("SuggestServiceImpl getSizeAvailable start with dto: {}", JsonUtils.toJson(dto));
        log.info("SuggestServiceImpl getSizeAvailable validate");
        SuggestValidator.validGetSizeAvailable(dto);
        com.ws.masterserver.entity.BodyHeightEntity height = repository.bodyHeightRepository.findByHeight(Long.valueOf(dto.getHeight()));
        com.ws.masterserver.entity.BodyWeightEntity weight = repository.bodyWeightRepository.findByWeight(Long.valueOf(dto.getWeight()));
        if (height == null || weight == null) {
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
        int code = height.getCode().getValue() - weight.getCode().getValue();
        SuggestEntity suggest;
        if (code < 0) {
            suggest = repository.suggestRepository.findByBodyWeightIdAndCategoryId(weight.getId(), dto.getCategoryId());
            if (suggest == null) {
                suggest = repository.suggestRepository.findByBodyWeightIdAndCategoryIdIsNull(weight.getId());
            }
        } else if (code == 0) {
            suggest = repository.suggestRepository.findByBodyWeightIdAndBodyHeightIdAndCategoryId(weight.getId(), height.getId(), dto.getCategoryId());
            if (suggest == null) {
                suggest = repository.suggestRepository.findByBodyWeightIdAndBodyHeightIdAndCategoryIdIsNull(weight.getId(), height.getId());
            }
        } else {
            suggest = repository.suggestRepository.findByBodyHeightIdAndCategoryId(height.getId(), dto.getCategoryId());
            if (suggest == null) {
                suggest = repository.suggestRepository.findByBodyHeightIdAndCategoryIdIsNull(height.getId());
            }
        }
        if (suggest == null) {
            throw new WsException(WsCode.SIZE_SUGGEST_NOT_FOUND);
        }
        com.ws.masterserver.entity.SizeEntity size = repository.sizeRepository.findById(suggest.getSizeId()).orElse(null);
        if (size == null) {
            throw new WsException(WsCode.SIZE_SUGGEST_NOT_FOUND);
        }
        log.info("SuggestServiceImpl getSizeAvailable finish with res: {}", JsonUtils.toJson(size));
        return ResData.ok(size);
    }
}
