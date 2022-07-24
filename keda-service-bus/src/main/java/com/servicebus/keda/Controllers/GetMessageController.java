package com.servicebus.keda.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.servicebus.keda.Entities.User;

@Component
public class GetMessageController {
    private static final String QUEUE_NAME = "ansalemo-queue";

    private final Logger logger = LoggerFactory.getLogger(GetMessageController.class);

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(User user) {
        logger.info("Received message: {}", user.getName());
    }
}
