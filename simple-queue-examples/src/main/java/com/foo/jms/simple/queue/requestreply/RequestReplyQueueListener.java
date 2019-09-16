package com.foo.jms.simple.queue.requestreply;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class RequestReplyQueueListener {

    @Qualifier("queue")
    @JmsListener(destination = "${com.foo.ibmmq.simple.queue.requestReplyQueue}")
    public String process(Message<String> message) {
        return "Reesponse for queue for :"+message.getPayload();
    }
}

