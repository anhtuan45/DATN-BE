package com.ws.masterserver.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class SlugUtils {

    public static String generateSlug(String name, String suffix) {
        log.info("generateSlug() name: {}, suffix: {}", name, suffix);
        if (StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(suffix)) {
            return "";
        }
        name += "-" + suffix;
        String result = StringUtils.removeAccentWithoutTrim(name).replace(" ", "-").toLowerCase(Locale.ROOT);
        return result;
    }
}
