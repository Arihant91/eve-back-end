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
@Table("orders_mean")
public class OrdersMeanEntity {

    @PrimaryKeyColumn(name = "region_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long regionId;

    @PrimaryKeyColumn(name = "location_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Long locationId;

    @PrimaryKeyColumn(name = "type_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private Long typeId;

    @PrimaryKeyColumn(name = "time_of_scraping", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private LocalDateTime timeOfScraping;

    @Column("is_buy_orders")
    private Boolean isBuyOrders;

    @Column("avg_price")
    private BigDecimal avgPrice;

    @Column("volume_remain")
    private Long volumeRemain;

    @Column("highest_price")
    private BigDecimal highestPrice;

    @Column("lowest_price")
    private BigDecimal lowestPrice;

    @Column("order_count")
    private Integer orderCount;
}
