package com.ws.masterserver.service;

import com.ws.masterserver.utils.base.rest.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author myname
 */
public interface FileService {
    Object uploadImage(CurrentUser currentUser, MultipartFile file) throws IOException;

    Object delete(CurrentUser currentUser, String url);
}
