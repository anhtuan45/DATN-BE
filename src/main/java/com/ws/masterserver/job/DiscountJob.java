package com.ws.masterserver.job;

import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class DiscountJob {
    private final WsRepository repository;

    @Async
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void handleChangeDiscountStatus() {
        log.info("handleChangeDiscountStatus() start");
        boolean isRun = repository.discountRepository.checkTask4Job(new Date());
        log.info("handleChangeDiscountStatus() isRun: {}", isRun);
        if (!isRun) {
            log.info("handleChangeDiscountStatus() done");
            return;
        }
        List<DiscountEntity> discountActiveList = repository.discountRepository.findActiveList4Job(new Date());
        log.info("handleChangeDiscountStatus() discountExpiredList: {}", JsonUtils.toJson(discountActiveList));
        if (discountActiveList.isEmpty()) {
            log.info("handleChangeDiscountStatus() done");
            return;
        }
        discountActiveList.forEach(o ->
            o.setStatus(DiscountStatusEnums.DE_ACTIVE.name())
        );
        log.info("handleChangeDiscountStatus() _discountExpiredList before save: {}", JsonUtils.toJson(discountActiveList));
        repository.discountRepository.saveAll(discountActiveList);
        log.info("handleChangeDiscountStatus() _discountExpiredList after save: {}", JsonUtils.toJson(discountActiveList));
        log.info("handleChangeDiscountStatus() done");
    }


}
