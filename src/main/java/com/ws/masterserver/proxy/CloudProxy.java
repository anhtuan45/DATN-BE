package com.ws.masterserver.proxy;

import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.common.CloudUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class CloudProxy {

    private final static String SECURE_URL = "secure_url";
    private final static String NOT_FOUND = "NOT FOUND";

    @Transactional
    public String upload(byte[] image) {
        log.info("upload() start with payload: {}", image);
        try {
            Map res = CloudUtils.upload(image, Collections.emptyMap());
            log.info("upload() res: {}", JsonUtils.toJson(res));
            return res.get(SECURE_URL).toString();
        } catch (Exception e) {
            log.info("upload() error: {}", e.getMessage());
            throw new WsException(WsCode.INTERNAL_SERVER);
        }
    }

    public String upload(MultipartFile file) throws IOException {
        log.info("upload() start with payload: {}", JsonUtils.toJson(file));
        return upload(file.getBytes());
    }

    public String upload(String base64String) {
        log.info("upload() start with payload: {}", base64String);
        return upload(Base64.getDecoder().decode(base64String));
    }

    @Transactional
    public Object delete(String url) {
        log.info("delete() start with payload: {}", url);
        String publicId = CloudUtils.getPublicIdFromUrl(url);
        log.info("delete() publicId: {}", publicId);
        try {
            Map res = (Map) CloudUtils.delete(publicId, Collections.emptyMap());
            log.info("delete() res: {}", JsonUtils.toJson(res));
            String result = res.get("result").toString();
            log.info("delete() result: {}", result);
            if (NOT_FOUND.equalsIgnoreCase(result)) {
                throw new WsException(WsCode.INTERNAL_SERVER);
            }
            return result;
        } catch (Exception e) {
            log.error("delete() error: {}", e.getMessage());
//            throw new WsException(WsCode.INTERNAL_SERVER);
            return null;
        }
    }
}
