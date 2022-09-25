package com.ws.masterserver.job;

import com.ws.masterserver.dto.dob.DiscountNotificationDto;
import com.ws.masterserver.service.MailService;
import com.ws.masterserver.utils.common.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class DiscountNotificationJob {

    private static Map<String, DiscountNotificationDto> data = new HashMap<>();
    private final MailService mailService;

    @Async
    @Scheduled(fixedRate = 1000 * 5)
    public void handleSendDiscountNotification() {
        log.info("handleSendDiscountNotification() start with data: {}", JsonUtils.toJson(data));
        if (data.isEmpty()) {
            log.info("handleSendDiscountNotification() data empty => done");
            return;
        }
        data.values().forEach(dto -> {
            log.info("handleSendDiscountNotification() dto: {}", JsonUtils.toJson(dto));
            mailService.sendDiscountNotification(dto);
            data.remove(dto.getDiscountId());
        });
        log.info("handleSendDiscountNotification() done!");

    }

    public static void add(DiscountNotificationDto dto) {
        if (!data.containsKey(dto.getDiscountId())) {
            data.put(dto.getDiscountId(), dto);
        }
    }
}
