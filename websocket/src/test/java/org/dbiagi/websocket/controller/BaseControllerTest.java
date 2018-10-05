package org.dbiagi.websocket.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {
    @Value("${local.server.port}")
    int port;

    WebSocketStompClient client;
    String URL;
    StompSession session;
    private static final String WEBSOCKET_ENDPOINT = "wb";
    private static final String MESSAGE_ENDPOINT = "message";

    @Before
    public void setup() throws InterruptedException, ExecutionException, TimeoutException {
        URL = String.format("ws://localhost:%d/%s", port, WEBSOCKET_ENDPOINT);

        client = new WebSocketStompClient(
            new SockJsClient(createTransportClient())
        );

        client.setMessageConverter(new MappingJackson2MessageConverter());
        session = client.connect(URL, new StompSessionHandlerAdapter() {}).get(1, SECONDS);
    }

    @Test
    public void testMessage() throws InterruptedException, ExecutionException, TimeoutException {

    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}
