package com.foo.jms.simple.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleSubscriber {

    @JmsListener(destination = "${com.foo.ibmmq.simple.topic}" , containerFactory = "topicJmsListenerContainerFactory")
    public void onPublish(Message<String> message) {
        log.info("Headers :{} ", message.getHeaders());
        log.info("Payload :{} ", message.getPayload());
    }
}
