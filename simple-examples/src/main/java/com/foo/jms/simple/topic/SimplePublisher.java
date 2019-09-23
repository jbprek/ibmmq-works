package com.foo.jms.simple.topic;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;

@Api(value="Publish a message to a topic", description = "Topic Publisher")
@RestController
@RequestMapping(path="/messages/publish")
public class SimplePublisher {

    @Value("${com.foo.ibmmq.simple.topic}")
    private String destinationName;

    private JmsTemplate jmsTemplate;

    public SimplePublisher( @Qualifier("topic") JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PutMapping("/{message}")
    public void publishMessage(@PathVariable("message") String message) {
        jmsTemplate.send(destinationName, session -> {
            TextMessage payload = session.createTextMessage();
            payload.setText(message);
            return payload;
        });
    }

}
