package com.example.demo_java.controller;

import com.example.demo_java.service.XChangeAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {

    private final XChangeAPIService xChangeAPIService;

    @Autowired
    public ExchangeRateController(XChangeAPIService xChangeAPIService) {
        this.xChangeAPIService = xChangeAPIService;
    }

    @GetMapping("/exchange-rates")
    public String getExchangeRates() throws IOException {
        return xChangeAPIService.fetchExchangeRates();
    }

    @GetMapping("/currency-pair")
    public String getCurrencyPair(@RequestParam() String currencyPair, @RequestParam String period) throws IOException {
        String url = """
                json/chart/%s/%s""".formatted(currencyPair, period);
        return xChangeAPIService.fetchData(url);
    }
}
