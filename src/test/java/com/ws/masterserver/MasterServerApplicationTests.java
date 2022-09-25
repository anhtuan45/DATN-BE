package com.ws.masterserver;

import com.ws.masterserver.utils.common.SlugUtils;
import com.ws.masterserver.utils.common.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
class MasterServerApplicationTests {

    @Test
    void contextLoads() {
        String regex = "^.*/(?<publicId>.*)\\.\\w*";
        String url = "https://res.cloudinary.com/hungnn22cloudinary/image/upload/v1662628036/bi70pioghlheyvfdw1xk.jpg";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String group = matcher.group("publicId");
            assertEquals("bi70pioghlheyvfdw1xk", group);
        }
    }

    public static String getPublicIdFromUrl(String url) {
        String regex = "^.*/(?<publicId>.*)\\.\\w*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.group("publicId");
    }

    @Test
    public void testSlug() {
        String category = "Áo khoác nam";
//        String type = "Nữ";
//        category += "" +  type;
        String slug = SlugUtils.generateSlug("Áo khoác gió","Nam");
        System.out.println("Slug"+slug);
        assertEquals(true, slug.length() > 0);
    }

}
