package com.servicebus.keda.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicebus.keda.Entities.User;

@RestController
public class SendMessageController {
    private static final String DESTINATION_NAME = "ansalemo-queue";

    private static final Logger logger = LoggerFactory.getLogger(SendMessageController.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/api/messages/send")
    public String postMessage(@RequestParam String message) {
        logger.info("Sending message" + message);
        jmsTemplate.convertAndSend(DESTINATION_NAME, new User(message));
        return message;
    }
}
