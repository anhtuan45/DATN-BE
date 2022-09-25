package com.ws.masterserver.utils.common;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloudUtils {
    @Value("${cloud.name}")
    private String name;

    @Value("${cloud.api.key}")
    private String apiKey;

    @Value("${cloud.api.secret}")
    private String apiSecret;

    public static Cloudinary getCloudinary() {
//        var config = new HashMap<>();
//        config.put("cloud_name", name);
//        config.put("api_key", apiKey);
//        config.put("api_secret", apiSecret);
        return new Cloudinary("cloudinary://384426152519968:WjFBrrrKlAs1vU42mePi4FJLPW0@hungnn22cloudinary");
    }

    public static String getPublicIdFromUrl(String url) {
        String regex = "^.*/(?<publicId>.*)\\.\\w*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group("publicId");
        }
        return null;
    }

    public static Map upload(byte[] image, Map options) throws IOException {
        return getCloudinary().uploader().upload(image, options);
    }

    public static Object delete(String publicId, Map options) throws IOException {
        return getCloudinary().uploader().destroy(publicId, options);
    }
}
