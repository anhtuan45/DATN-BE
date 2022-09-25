package com.ws.masterserver.service.impl;

import com.ws.masterserver.proxy.CloudProxy;
import com.ws.masterserver.service.FileService;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
import com.ws.masterserver.utils.validator.file.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final CloudProxy cloudProxy;

    @Override
    public Object uploadImage(CurrentUser currentUser, MultipartFile file) throws IOException {
        AuthValidator.checkAdmin(currentUser);
        FileValidator.validImageFile(file);
        return cloudProxy.upload(file);
    }

    @Override
    public Object delete(CurrentUser currentUser, String url) {
        AuthValidator.checkAdmin(currentUser);
        return cloudProxy.delete(url);
    }
}
