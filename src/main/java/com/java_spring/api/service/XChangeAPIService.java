package com.java_spring.api.service;

import com.java_spring.api.config.XChangeConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;

import static com.java_spring.api.constant.XChangeConstant.API_BASE_URL;
import static com.java_spring.api.constant.XChangeConstant.API_KEY;

@Service
public class XChangeAPIService {
    private final OkHttpClient client;
    private final XChangeConfig xChangeConfig;

    @Autowired
    public XChangeAPIService(XChangeConfig xChangeConfig) {
        this.client = new OkHttpClient();
        this.xChangeConfig = xChangeConfig;
    }

    public String fetchData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(xChangeConfig.getApiBaseUrl() +url)
                .addHeader("api-key", xChangeConfig.getApiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
