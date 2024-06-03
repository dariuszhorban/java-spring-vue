package com.java_spring.api.controller;

import com.java_spring.api.service.XChangeAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class XChangeController {

    private final XChangeAPIService xChangeAPIService;

    @Autowired
    public XChangeController(XChangeAPIService xChangeAPIService) {
        this.xChangeAPIService = xChangeAPIService;
    }

    @GetMapping("/currency-pair")
    public String getCurrencyPair(@RequestParam() String currencyPair, @RequestParam String period) throws IOException {
        String url = """
                json/chart/%s/%s""".formatted(currencyPair, period);
        return xChangeAPIService.fetchData(url);
    }
}
