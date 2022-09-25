package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.suggest.CategoryDto;
import com.ws.masterserver.dto.customer.suggest.SuggestDto;

import java.util.List;

public interface SuggestService {
    List<CategoryDto> getCategories();

    Object getSizeAvailable(SuggestDto dto);
}
