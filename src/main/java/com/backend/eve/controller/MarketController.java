package com.backend.eve.controller;


import com.backend.eve.model.Entity;
import com.backend.eve.model.GroupDetails;
import com.backend.eve.model.OrdersMean;
import com.backend.eve.service.CassandraService;
import com.backend.eve.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MarketController {
    private final MarketService marketService;
    private final CassandraService cassandraService;
    @Autowired
    public MarketController(MarketService marketService, CassandraService cassandraService) {
        this.marketService = marketService;
        this.cassandraService = cassandraService;
    }

    @QueryMapping
    public String getMarketDetails() {
        return marketService.getMarketStructure();
    }

    @QueryMapping
    public List<Entity> getRegions(){
        return marketService.getRegions();
    }

    @QueryMapping
    public List<OrdersMean> getOrdersMeanInRegion(@Argument Long regionId,@Argument Long typeId,@Argument String startDate,@Argument String endDate, @Argument Boolean isBuyOrder){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        return cassandraService.getRecords(regionId, typeId, LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter),isBuyOrder);
    }

}
