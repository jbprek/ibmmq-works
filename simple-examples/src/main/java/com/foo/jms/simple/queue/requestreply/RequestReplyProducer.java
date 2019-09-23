package com.foo.jms.simple.queue.requestreply;

import io.swagger.annotations.Api;
import lombok.Data;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.time.Instant;

@Api(value = "Send Message to a Queue, synchronously get  Teply", description = "Request Reply")
@RestController
@RequestMapping(path = "/messages/requestReponse")
public class RequestReplyProducer {

    @Data
    static class MessageReceipt {
        private String request;
        private Instant requestTimestamp;
        private String response;
        private Instant responseTimestamp;
    }

    @Value("${com.foo.ibmmq.simple.queue}")
    private String destinationName;

    private JmsTemplate jmsTemplate;

    public RequestReplyProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/{message}")
    public MessageReceipt requestResponse(@PathVariable("message") String message) throws JMSException {
        val receipt = new MessageReceipt();
        receipt.setRequest(message);
        receipt.setRequestTimestamp(Instant.now());
        val responseMessage = jmsTemplate.sendAndReceive(destinationName, session -> {
            TextMessage payload = session.createTextMessage();
            payload.setText(message);
            return payload;
        });
        if (responseMessage != null) {
            receipt.setResponse(((TextMessage) responseMessage).getText());
        }
        receipt.setResponseTimestamp(Instant.now());
        return receipt;
    }
}
