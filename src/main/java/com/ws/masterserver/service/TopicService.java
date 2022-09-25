package com.ws.masterserver.service;

import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.dto.customer.topic.TopicResponse;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.ResData;

import java.util.List;

public interface TopicService {

        ResData<List<TopicResponse>> getAllTopic();





}
