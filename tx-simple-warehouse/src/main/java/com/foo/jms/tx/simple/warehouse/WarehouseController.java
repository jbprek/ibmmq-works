package com.foo.jms.tx.simple.warehouse;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@Api(value = "Warehouse control", description = "Warehouse contro")
@RestController
@RequestMapping(path = "/warehouseOps")
@Slf4j
public class WarehouseController {

    private static final AtomicBoolean status = new AtomicBoolean(true);

    @Value("${spring.jms.template.default-destination}")
    private String destinationName;


    @PutMapping("/toggleStatus")
    public Boolean toggleStatus() {
        boolean temp;
        do temp = status.get(); while (!status.compareAndSet(temp, !temp));
        return !temp;
    }

    @Transactional
    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void onMessage(Message<String> message) {
        log.info("Headers :{} ", message.getHeaders());
        log.info("Payload :{} ", message.getPayload());
        if (!status.get()) {
            throw new WarehouseOutOfOrderException();
        }
    }

}

