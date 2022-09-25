package com.ws.masterserver;

import com.ws.masterserver.entity.OrderEntity;
import com.ws.masterserver.entity.OrderStatusEntity;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.constants.enums.StatusEnum;
import com.ws.masterserver.utils.seeder.BatchSeeder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
@EnableScheduling
public class MasterServerApplication {

    @PostConstruct
    public void init() {
        log.info("init() start");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }

    public static void main(String[] args) {
        SpringApplication.run(MasterServerApplication.class, args);
        PasswordEncoder passwordEncoder = BeanUtils.getBean(BCryptPasswordEncoder.class);
        log.info("admin123 encode: {}",passwordEncoder.encode("admin123"));
        try {
//            new BatchSeeder().seed();
//            updateOrderStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateOrderStatus() {
        WsRepository repository = BeanUtils.getBean(WsRepository.class);
        List<OrderEntity> orderEntities = repository.orderRepository.findAll();
        if (!orderEntities.isEmpty()) {
            for (OrderEntity order : orderEntities) {
                StatusEnum statusEnum = getStatusEnum(order.getStatus());
                if (statusEnum != null) {
                    order.setStatus(statusEnum.name());
                    repository.orderRepository.save(order);
                }
            }
        }

        List<OrderStatusEntity> orderStatusEntities = repository.orderStatusRepository.findAll();
        if (!orderStatusEntities.isEmpty()) {
            for (OrderStatusEntity orderStatus : orderStatusEntities) {
                StatusEnum statusEnum = getStatusEnum(orderStatus.getStatus());
                if (statusEnum != null) {
                    orderStatus.setStatus(statusEnum.name());
                    repository.orderStatusRepository.save(orderStatus);
                }
            }
        }
    }

    private static StatusEnum getStatusEnum(String status) {
        StatusEnum statusEnum = null;
        switch (status) {
            case "CANCEL":
                statusEnum = StatusEnum.CANCELED;
                break;
            case "REJECT":
                statusEnum = StatusEnum.REJECTED;
                break;
            case "ACCPECT":
                statusEnum = StatusEnum.ACCEPTED;
                break;
            default:
                break;
        }
        return statusEnum;
    }

}
