package com.ws.masterserver.utils.validator.file;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileValidator {

    private static final Long MAX_SIZE_IMAGE = 5 * 1000000L;

    public static void validImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            log.error("validImageFile() error: file empty");
            throw new WsException(WsCode.FILE_INVALID);
        }
        if (!file.getContentType().startsWith("image")) {
            throw new WsException(WsCode.FILE_INVALID);
        }
        if (MAX_SIZE_IMAGE <= file.getSize()) {
            throw new WsException(WsCode.FILE_SIZE_OVERLOAD);
        }

    }
}
