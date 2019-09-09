package com.foo.jms.simple.queue.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
@Slf4j

public class SimpleQueueListener {

    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void process(Message<String> message) {
        log.info("Headers :\n{} \n",message.getHeaders());
        log.info("Payload :\n{} \n",message.getPayload());
    }
}
