package com.ws.masterserver.controller;

import com.ws.masterserver.utils.base.WsController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController extends WsController {

    @PostMapping("/v1/file/upload/image")
    public Object uploadMultipart(MultipartFile file) throws IOException {
        return service.fileService.uploadImage(getCurrentUser(), file);
    }

    @DeleteMapping("/v1/file/delete")
    public Object deleteFile(String url) {
        return service.fileService.delete(getCurrentUser(), url);
    }

}
