package com.foo.jms.simple.queue.requestreply;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class RequestReplyQueueListener {

    @JmsListener(destination = "${com.foo.ibmmq.simple.queue}")
    public String onRequest(Message<String> message) {
        log.info("Headers :{} ",message.getHeaders());
        log.info("Payload :{} ",message.getPayload());
        return "Queue Response for Request:"+message.getPayload();
    }
}

