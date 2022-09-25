package com.ws.masterserver.utils.common;

import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class JpaUtils {

    private JpaUtils() {}

    @Nullable
    public static String getString(@Nullable Object object) {
        return Optional.ofNullable(object)
                .map(String::valueOf)
                .orElse(null);
    }

    @Nullable
    public static Long getLong(@Nullable Object object) {
        return Optional.ofNullable(getString(object))
                .map(Long::valueOf)
                .orElse(null);
    }

    @Nullable
    public static Integer getInt(@Nullable Object object) {
        return Optional.ofNullable(getString(object))
                .map(Integer::valueOf)
                .orElse(null);
    }

    @Nullable
    public static Float getFloat(@Nullable Object object) {
        return Optional.ofNullable(getString(object))
                .map(Float::valueOf)
                .orElse(null);
    }

    @Nullable
    public static Double getDouble(@Nullable Object object) {
        return Optional.ofNullable(getString(object))
                .map(Double::valueOf)
                .orElse(null);
    }

    @Nullable
    public static Date getDate(@Nullable Object object, String... pattern) {
        return Optional.ofNullable(getString(object))
                .map(str -> {
                    try {
                        return pattern.length == 0
                                ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s").parse(str)
                                : new SimpleDateFormat(pattern[0]).parse(str);

                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .orElse(null);
    }

    @Nullable
    public static UUID getUUID(@Nullable Object object) {
        return Optional.ofNullable(getString(object))
                .map(UUID::fromString)
                .orElse(null);
    }

    @Nullable
    public static LocalDateTime getLocalDateTime(@Nullable Object o) {
        return Optional.ofNullable((LocalDateTime) o).orElse(null);
    }

    @Nullable
    public static Boolean getBoolean(@Nullable Object object) {
        return Optional.ofNullable(getString(object))
                .map(Boolean::valueOf)
                .orElse(null);
    }

    @Nullable
    public static Timestamp getTimeStamp(@Nullable Object o) {
        return (Timestamp) o;
    }
}
