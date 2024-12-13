package com.backend.eve.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersStatsByLocation {

    private Long regionId;

    private Long locationId;

    private Long typeId;

    private LocalDateTime timeOfScraping;

    private Boolean isBuyOrders;

    private BigDecimal avgPrice;

    private BigDecimal medianPrice;

    private Long volumeRemain;

    private BigDecimal highestPrice;

    private BigDecimal lowestPrice;

    private Integer orderCount;

    private BigDecimal stdDeviation;
}
