package com.ws.masterserver.controller;

import com.ws.masterserver.dto.admin.blog.search.BlogReq;
import com.ws.masterserver.dto.admin.category.CategoryReq;
import com.ws.masterserver.dto.admin.category.CategoryRes;
import com.ws.masterserver.dto.admin.size.search.SizeReq;
import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
@Slf4j
public class BlogController extends WsController {

    @Operation(summary = "API lấy tất cả post blog")
    @GetMapping("")
    public ResponseEntity<?> getAllBlog() {
        log.info("START API /api/v1/blog");
        return ResponseEntity.status(HttpStatus.OK).body(service.blogService.getAllBlog());
    }
    @Operation(summary = "API lấy blogs theo topic")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllBlogbyTopic(@PathVariable("id") String id) {
        log.info("START API /api/v1/blog",id);
        return ResponseEntity.status(HttpStatus.OK).body(service.blogService.getAllBlogByTopic(id));
    }
    @Operation(summary = "API lấy chi tiết blog post")
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getBlogDetail(@PathVariable("id") String id) {
        log.info("START API /api/v1/blog/detail",id);
        return ResponseEntity.status(HttpStatus.OK).body(service.blogService.getBlogDetail(id));
    }


    @GetMapping("/admin/detail/{id}")
    @Operation(summary = "API admin chi tiết blog")
    public Object detail(@PathVariable("id") String id) {
        log.info("start api detail with dto: {}", id);
        return service.blogService.detail(getCurrentUser(), id);
    }

    @PostMapping("/create")
    @Operation(summary = "API create blog")
    public ResponseEntity<?> createNewBlog(@RequestBody BlogResponse dto){
        log.info("------ start api create blog -----");
        return ResponseEntity.ok(service.blogService.create(getCurrentUser(),dto));
    }
    @PostMapping("/update")
    @Operation(summary = "API update blog")
    public ResponseEntity<?> updateBlog(@RequestBody BlogResponse dto){
        log.info("------ start api update blog -----");
        return ResponseEntity.ok(service.blogService.update(getCurrentUser(),dto));
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "API delete blog")
    public ResponseEntity<?> deleteBlogPost(@PathVariable("id") String id){
        log.info("------ start api delete blog -----");
        return ResponseEntity.ok(service.blogService.delete(getCurrentUser(),id));
    }

    @PostMapping("/change-status/{id}")
    @Operation(summary = "API changeStatus blog")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id){
        log.info("------ start api change status blog -----");
        return ResponseEntity.ok(service.blogService.changeStatus(getCurrentUser(),id));
    }

    @PostMapping("/search")
    @Operation(summary = "API tìm kiếm blog")
    public ResponseEntity<PageData<BlogResponse>> search(@RequestBody BlogReq req) {
        log.info("start api search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.blogService.search(getCurrentUser(), req));
    }
    @PostMapping("/searchV2")
    @Operation(summary = "API tìm kiếm blog")
    public ResponseEntity<PageData<BlogResponse>> searchV2(@RequestBody BlogReq req) {
        log.info("start api search with req: {}", JsonUtils.toJson(req));
        return ResponseEntity.ok(service.blogService.searchV2(req));
    }


}
