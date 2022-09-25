package com.ws.masterserver.service.impl;

import com.ws.masterserver.dto.admin.blog.search.BlogReq;
import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.entity.*;
import com.ws.masterserver.service.BlogService;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.ResData;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogServiceImpl implements BlogService {
    private final WsRepository repository;

    @Override
    public ResData<List<BlogResponse>> getAllBlog() {
        List<BlogResponse> blog = repository.blogRepository.getAllBlog();
        return new ResData<>(blog, WsCode.OK);
    }

    @Override
    public ResData<List<BlogResponse>> getAllBlogByTopic(String id) {
        List<BlogResponse> blog = repository.blogRepository.getAllBlogByTopic(id);
        return new ResData<>(blog, WsCode.OK);
    }

    @Override
    public ResData<List<BlogResponse>> getBlogDetail(String id) {
        List<BlogResponse> blog = repository.blogRepository.getBlogDetail(id);
        return new ResData<>(blog, WsCode.OK);
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

    @Override
    public Object detail(CurrentUser currentUser, String id) {
        AuthValidator.checkAdmin(currentUser);
        BlogEntity blog = repository.blogRepository.findById(id).orElseThrow(() -> {
            throw new WsException(WsCode.ERROR_NOT_FOUND);
        });
        return BlogResponse.builder()
                .id(blog.getId())
                .content(blog.getContent())
                .createdDate(blog.getCreatedDate())
                .title(blog.getTitle())
                .topicId(blog.getTopicId())
                .image(blog.getImage())
//                .name(blog.getName())
                .build();
    }
    @Override
    public ResData<String> create(CurrentUser currentUser, BlogResponse dto) {
        AuthValidator.checkAdmin(currentUser);
        BlogEntity blog = BlogEntity.builder()
                .id(UidUtils.generateUid())
                .content(dto.getContent())
                .createdDate(new Date())
                .title(dto.getTitle())
                .topicId(dto.getTopicId())
                .description(dto.getDescription())
                .image(dto.getImage())
                .active(true)
                .build();
        repository.blogRepository.save(blog);
        log.info("create finished at {} with response: {}", new Date(), JsonUtils.toJson(blog));
        return new ResData<>(blog.getId(), WsCode.OK);
    }


    @Override
    public Object update(CurrentUser currentUser, BlogResponse dto) {
        AuthValidator.checkAdmin(currentUser);
        BlogEntity blog = repository.blogRepository.findByIdAndActive(dto.getId(), Boolean.TRUE);
        if (blog == null) {
            throw new WsException(WsCode.BLOG_NOT_FOUND);
        }

        if(dto.getTitle()==null||dto.getTitle().isEmpty()||dto.getTitle().equals("")){
            blog.setTitle(blog.getTitle());
        }else{
            blog.setTitle(dto.getTitle());
        }
//        ==
        if(dto.getTopicId()==null||dto.getTopicId().isEmpty()||dto.getTopicId().equals("")){
            blog.setTopicId(blog.getTopicId());
        }else{
            blog.setTopicId(dto.getTopicId());
        }
//        ==
        if(dto.getContent()==null||dto.getContent().isEmpty()||dto.getContent().equals("")){
            blog.setContent(blog.getContent());
        }else{
            blog.setContent(dto.getContent());
        }
//        ==
        if(dto.getTopicId()==null||dto.getTopicId().isEmpty()||dto.getTopicId().equals("")){
            blog.setTopicId(blog.getTopicId());
        }else{
            blog.setTopicId(dto.getTopicId());
        }
//        ==
        if(dto.getImage()==null||dto.getImage().isEmpty()||dto.getImage().equals("")){
            blog.setImage(blog.getImage());
        }else{
            blog.setImage(dto.getImage());
        }
//        ==
        if(dto.getDescription()==null||dto.getDescription().isEmpty()||dto.getDescription().equals("")){
            blog.setDescription(blog.getDescription());
        }else{
            blog.setDescription(dto.getDescription());
        }
//        ==
        blog.setCreatedDate(new Date());

        repository.blogRepository.save(blog);
        log.info("update finished at {} with response: {}", new Date(), JsonUtils.toJson(blog));
        return new ResData<>(blog.getId(), WsCode.OK);
    }


    @Override
    public ResData<String> delete(CurrentUser currentUser,  String id) {
        AuthValidator.checkAdmin(currentUser);
        if (id == null || Boolean.FALSE.equals(repository.blogRepository.findByIdAndActive(id, Boolean.TRUE))) {
            throw new WsException(WsCode.BLOG_NOT_FOUND);
        }
        BlogEntity blog = repository.blogRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        repository.blogRepository.delete(blog);
        log.info("delete finished at {} with response: {}", new Date(), JsonUtils.toJson(blog));
        return new ResData<>(blog.getId(), WsCode.OK);
    }


    @Override
    public ResData<String> changeStatus(CurrentUser currentUser,  String id) {
        AuthValidator.checkAdmin(currentUser);
        BlogEntity blog = repository.blogRepository.findById(id).orElseThrow(() -> new WsException(WsCode.ERROR_NOT_FOUND));
        blog.setActive(!blog.getActive());
        repository.blogRepository.save(blog);
        log.info("change-status finished at {} with response: {}", new Date(), JsonUtils.toJson(blog));
        return new ResData<>(blog.getId(), WsCode.OK);
    }


    @Override
    public PageData<BlogResponse> search(CurrentUser currentUser, BlogReq req) {
        AuthValidator.checkAdminAndStaff(currentUser);
        if (StringUtils.isNullOrEmpty(req.getTextSearch())) {
            req.setTextSearch("");
        }
        String textSearch = req.getTextSearch().trim().toUpperCase(Locale.ROOT);
        Pageable pageable = PageableUtils.getPageable(req.getPageReq());
        Page<BlogEntity> blogPage = repository.blogRepository.search(textSearch, req.getActive(), req.getTopicId(), pageable);
        return PageData.setResult(blogPage.getContent().stream().map(o -> {
                    BlogEntity blogEntity = repository.blogRepository.findById(o.getId()).orElse(null);
                    return BlogResponse.builder()
                            .id(o.getId())
                            .name(o.getName())
                            .title(o.getTitle())
                            .image(o.getImage())
                            .description(o.getDescription())
                            .active(o.getActive())
                            .createdDate(o.getCreatedDate())
                            .topicId(o.getTopicId())
                            .content(o.getContent())
                            .build();
                }).collect(Collectors.toList()),
                blogPage.getNumber(),
                blogPage.getSize(),
                blogPage.getTotalElements());
    }


    @Override
    public PageData<BlogResponse> searchV2(BlogReq req) {
        if (StringUtils.isNullOrEmpty(req.getTextSearch())) {
            req.setTextSearch("");
        }
        String textSearch = req.getTextSearch().trim().toUpperCase(Locale.ROOT);
        Pageable pageable = PageableUtils.getPageable(req.getPageReq());
        Page<BlogEntity> blogPage = repository.blogRepository.search(textSearch, req.getActive(), req.getTopicId(), pageable);
        return PageData.setResult(blogPage.getContent().stream().map(o -> {
                    BlogEntity blogEntity = repository.blogRepository.findById(o.getId()).orElse(null);
                    return BlogResponse.builder()
                            .id(o.getId())
                            .name(o.getName())
                            .title(o.getTitle())
                            .image(o.getImage())
                            .description(o.getDescription())
                            .active(o.getActive())
                            .createdDate(o.getCreatedDate())
                            .topicId(o.getTopicId())
                            .content(o.getContent())
                            .build();
                }).collect(Collectors.toList()),
                blogPage.getNumber(),
                blogPage.getSize(),
                blogPage.getTotalElements());
    }

}
