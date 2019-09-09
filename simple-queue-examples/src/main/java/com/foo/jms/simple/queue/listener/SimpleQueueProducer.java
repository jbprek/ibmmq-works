package com.foo.jms.simple.queue.listener;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.val;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Api(value="Send Message to a Queue", description = "Fire And Forget")
@RestController
@RequestMapping(path="/messages/simpleProducer")
@AllArgsConstructor
public class SimpleQueueProducer {


    @Data(staticConstructor = "of")
    public static class MessageReceipt {
        private Instant timestamp = Instant.now();
        @NonNull
        private String content;
    }

    private JmsTemplate jmsTemplate;

    @GetMapping("/{message}")
    public MessageReceipt sentMessage(@PathVariable("message") String message) {
        val response = MessageReceipt.of(message);
        jmsTemplate.convertAndSend(message);
        return response;
    }

}
