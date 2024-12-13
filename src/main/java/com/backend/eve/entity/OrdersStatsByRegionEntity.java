package com.backend.eve.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Table("orders_stats_by_region")
public class OrdersStatsByRegionEntity {

    @PrimaryKeyColumn(name = "region_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long regionId;

    @Column("type_id")
    private Long typeId;

    @PrimaryKeyColumn(name = "time_of_scraping", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private LocalDateTime timeOfScraping;

    @Column("is_buy_orders")
    private Boolean isBuyOrders;

    @Column("avg_price")
    private BigDecimal avgPrice;

    @Column("median_price")
    private BigDecimal medianPrice;

    @Column("volume_remain")
    private Long volumeRemain;

    @Column("highest_price")
    private BigDecimal highestPrice;

    @Column("lowest_price")
    private BigDecimal lowestPrice;

    @Column("order_count")
    private Integer orderCount;

    @Column("std_deviation")
    private BigDecimal stdDeviation;
}
