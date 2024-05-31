package com.example.demo_java.controller;

import com.example.demo_java.service.XChangeAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final XChangeAPIService xChangeAPIService;

    @Autowired
    public WebSocketController(XChangeAPIService xChangeAPIService) {
        this.xChangeAPIService = xChangeAPIService;
    }

    @MessageMapping("/subscribe-exchange-rates") // Listen for subscription requests for exchange rates
    public void handleSubscribeToExchangeRates(String message) {
        System.out.println(message);
        if ("/topic/exchange-rates".equals(message)) {
            xChangeAPIService.establishWebSocketConnection(new String[]{"EURUSD", "CHFGBP"}); // Establish WebSocket connection to fetch exchange rates
        }
    }

    @MessageMapping("/unsubscribe-exchange-rates") // Listen for unsubscription requests for exchange rates
    public void handleUnsubscribeFromExchangeRates(String message) {
        if ("/topic/exchange-rates".equals(message)) {
            // Handle unsubscribe logic if needed
        }
    }

}
