package com.ws.masterserver.service;

import com.ws.masterserver.dto.admin.blog.search.BlogReq;
import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;

import java.util.List;

public interface BlogService {

        ResData<List<BlogResponse>> getAllBlog();
    ResData<List<BlogResponse>> getAllBlogByTopic(String id);
    ResData<List<BlogResponse>> getBlogDetail(String id);
    Object detail(CurrentUser currentUser, String id);

    ResData<String> create(CurrentUser currentUser, BlogResponse dto);

    ResData<String> delete(CurrentUser currentUser, String id);
    ResData<String> changeStatus(CurrentUser currentUser, String id);
    Object update(CurrentUser currentUser, BlogResponse dto);

    PageData<BlogResponse> search(CurrentUser currentUser, BlogReq req) throws WsException;
    PageData<BlogResponse> searchV2(BlogReq req) throws WsException;



}
