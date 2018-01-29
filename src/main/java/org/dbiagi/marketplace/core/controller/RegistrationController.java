package org.dbiagi.marketplace.core.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @RequestMapping("/singup")
    public ObjectNode singup() {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("a", 1);
        objectNode.put("b", 2);
        objectNode.put("c", 3);
        objectNode.put("d", 4);

        return objectNode;
    }
}
