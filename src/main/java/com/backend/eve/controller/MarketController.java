package com.backend.eve.controller;


import com.backend.eve.model.MarketGroups;
import com.backend.eve.service.MarketService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MarketController {
    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @QueryMapping
    public MarketGroups getMarketDetails() {
        return marketService.getMarketStructure();
    }

}
