package com.ws.masterserver.repository.custom;

import com.ws.masterserver.dto.admin.category.CategoryReq;
import com.ws.masterserver.dto.admin.category.CategoryRes;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;

public interface CategoryCustomRepository {
    PageData<CategoryRes> search(CurrentUser currentUser, CategoryReq req);
}
