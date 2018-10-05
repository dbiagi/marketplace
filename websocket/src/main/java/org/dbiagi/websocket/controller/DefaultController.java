package org.dbiagi.websocket.controller;

import org.dbiagi.websocket.dto.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class DefaultController {
    @MessageMapping("/message")
    @SendTo("/message")
    public Message send(Message message) {
        return message;
    }
}
