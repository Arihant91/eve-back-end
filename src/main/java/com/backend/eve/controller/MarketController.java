package com.backend.eve.controller;


import com.backend.eve.model.Entity;
import com.backend.eve.model.GroupDetails;
import com.backend.eve.service.MarketService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MarketController {
    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @QueryMapping
    public String getMarketDetails() {
        return marketService.getMarketStructure();
    }

    @QueryMapping
    public List<Entity> getRegions(){
        return marketService.getRegions();
    }

}
