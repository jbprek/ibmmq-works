package com.foo.jms.simple.pubsub;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;
import java.time.Instant;

@Api(value="Send Message to a Queue", description = "Fire And Forget")
@RestController
@RequestMapping(path="/messages/publish")
public class SimplePublisher {


    private JmsTemplate jmsTemplate;

    @Value("${com.foo.ibmmq.simple.topic}")
    private String destinationName;

    public SimplePublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/{message}")
    public void publish(@PathVariable("message") String message) {
        jmsTemplate.sendAndReceive(destinationName, session ->{
            TextMessage payload = session.createTextMessage();
            payload.setText(message);
            return payload;
        });
    }

}
