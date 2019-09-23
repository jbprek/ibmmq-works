package com.foo.jms.simple.queue.listener;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;

@Api(value="Send Message to a Queue", description = "Fire And Forget")
@RestController
@RequestMapping(path="/messages/simpleProducer")
@Slf4j
public class SimpleQueueProducer {

    @Value("${spring.jms.template.default-destination}")
    private String destinationName;

    private JmsTemplate jmsTemplate;

    public SimpleQueueProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PutMapping("/{message}")
    public void putInQueue(@PathVariable("message") String message) {
        log.info("Putting : {}", message);
        jmsTemplate.send(destinationName, session -> {
            TextMessage payload = session.createTextMessage();
            payload.setText(message);
            return payload;
        });
    }

}
