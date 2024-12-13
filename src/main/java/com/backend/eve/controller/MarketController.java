package com.backend.eve.controller;


import com.backend.eve.model.*;
import com.backend.eve.service.CassandraService;
import com.backend.eve.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    public List<GroupDetails> getMarketDetails() {
        return marketService.getMarketStructure();
    }

    @QueryMapping
    public List<Entity> getRegions() {
        return marketService.getRegions();
    }

    @QueryMapping
    public List<OrdersStatsByRegion> getOrdersStatsByRegion(@Argument List<Long> regionId, @Argument List<Long> typeId, @Argument String startDate, @Argument String endDate, @Argument Boolean isBuyOrder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        return cassandraService.getOrdersByRegion(regionId, typeId, LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter), isBuyOrder);
    }

    @QueryMapping
    public List<OrdersStatsByLocation> getOrdersStatsByLocation(@Argument List<Long> locationId, @Argument List<Long> typeId, @Argument String startDate, @Argument String endDate, @Argument Boolean isBuyOrder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        return cassandraService.getOrdersByLocation(locationId, typeId, LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter), isBuyOrder);
    }

    @QueryMapping
    public StructuresByRegion getStructuresByRegion(@Argument Long regionId){
        return cassandraService.getStructuresByRegion(regionId);
    }

    @QueryMapping
    public List<SortedByCharHash> getAllItems() {
        Map<String, List<Item>> itemMap = marketService.getAllItems();

        return itemMap.entrySet().stream()
                .map(entry -> {
                    return SortedByCharHash
                        .builder()
                        .key(entry.getKey().toLowerCase())
                        .items(entry.getValue())
                        .build();
                })
                .toList();
    }

}
