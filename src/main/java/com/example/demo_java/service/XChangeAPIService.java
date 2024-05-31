package com.example.demo_java.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;


@Service
public class XChangeAPIService {
    private static final String API_BASE_URL = "https://api.xchangeapi.com/";
    private static final String API_URL = "https://api.xchangeapi.com/latest?base=EUR";
    private static final String API_KEY = "8QgcRZVATG2kYYoRFSjptU5kvcx8Wzto";
    private final SimpMessagingTemplate messagingTemplate;
    private final OkHttpClient client;

    @Autowired
    public XChangeAPIService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.client = new OkHttpClient();
    }

    public void establishWebSocketConnection(String[] pair) {
        String url = "wss://api.xchangeapi.com/websocket/live";
        url += "?api-key=" + API_KEY;

        // Create a JSON object
        JSONObject jsonObject = new JSONObject();

        // Create a JSON array and put pair into it
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(pair);

        // Put the JSON array into the JSON object
        jsonObject.put("pairs", jsonArray);

        // Print JSON object
        System.out.println(jsonObject.toString());

        System.out.println(url);
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketHandler webSocketHandler = new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                // Connection established, send initial message or perform other tasks
                System.out.println("WebSocket connection established");
                // Send the initial message

                session.sendMessage(new TextMessage(jsonObject.toString()));
            }

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                // Handle incoming messages
                System.out.println("Received message: " + message.getPayload());
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                // Handle transport error, e.g., connection failure
                System.out.println("WebSocket connection error: " + exception.getMessage());
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                // Connection closed, perform cleanup tasks if needed
                System.out.println("WebSocket connection closed");
                System.out.println(closeStatus);
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        };

        client.doHandshake(webSocketHandler, url);
    }

    public String fetchExchangeRates() throws IOException {
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("api-key", API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String fetchData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(API_BASE_URL +url)
                .addHeader("api-key", API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
