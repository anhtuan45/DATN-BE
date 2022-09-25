package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.topic.TopicResponse;
import com.ws.masterserver.entity.BlogEntity;
import com.ws.masterserver.service.BlogService;
import com.ws.masterserver.service.TopicService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicService {
    private final WsRepository repository;

    @Override
    public ResData<List<TopicResponse>> getAllTopic() {
        List<TopicResponse> topic = repository.topicRepository.getAllTopic();
        return new ResData<>(topic, WsCode.OK);
    }

//    @Override
//    public ResData<List<BlogResponse>> getAllBlog() {
//        List<BlogResponse> blog = repository.blogRepository.getAllBlog();
//        return new ResData<>(blog, WsCode.OK);
//    }

//    @Override
//    public ResData<List<BlogResponse>> getAllBlogActive() {
//        List<BlogResponse> blog = repository.blogRepository.getAllBlogActive();
//        return new ResData<>(blog, WsCode.OK);
//    }



}
