package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.reset_password.ResetPasswordDto;
import com.ws.masterserver.dto.customer.user.register.RegisterDto;
import com.ws.masterserver.utils.base.WsController;
import com.ws.masterserver.utils.common.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/no-auth")
@RequiredArgsConstructor
@Slf4j
public class NoAuthController extends WsController {

    @Operation(summary = "KH dang ky")
    @PostMapping("/customer/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto payload) {
        log.info("start api /api/v1/no-auth/customer/register with payload: {}", JsonUtils.toJson(payload));
        return ResponseEntity.ok(service.customerDetailService.register(payload));
    }

    @Operation(summary = "gui mail khi quen mat khau")
    @GetMapping("/forgot-password/send-mail")
    public ResponseEntity<Object> sendMail(String email) {
        log.info("start api /api/v1/no-auth/forgot-password/send-mail with payload: {}", email);
        return ResponseEntity.ok(service.customerDetailService.sendMail4ForgotPass(email));
    }

    @Operation(summary = "api reset pass")
    @PostMapping("/reset-password")
    public Object resetPassword(@RequestBody ResetPasswordDto payload) {
        log.info("start api /api/v1/no-auth/reset-password with payload: {}", JsonUtils.toJson(payload));
        return service.customerDetailService.resetPassword(payload);
    }

    @GetMapping("/type/no-page")
    public Object typeNoPage() {
        log.info("start api /api/v1/no-auth/type/no-page");
        return service.typeService.noPage();
    }

    @GetMapping("/role/search")
    public Object roleSearch() {
        log.info("start api /api/v1/no-auth/role/search");
        return service.roleNoAuthService.search();
    }

    @GetMapping("/role/modify")
    public Object roleModify() {
        log.info("start api /api/v1/no-auth/role/modify");
        return service.roleNoAuthService.modify();
    }

    @GetMapping("/category/no-page")
    public Object categoryNoPage() {
        return service.categoryInfoService.noPage();
    }

    @GetMapping("/size/no-page")
    public Object sizeNoPage() {
        return service.sizeService.noPage();
    }

    @GetMapping("/color/no-page")
    public Object colorNoPage() {
        return service.colorService.noPage();
    }

    @GetMapping("/material/no-page")
    public Object materialNoPage() {
        return service.materialService.noPage();
    }

    @GetMapping("/category-slug/no-page")
    public Object categorySlugNoPage(){
        return service.categoryService.getListCategoryAndSlug();
    }

}
