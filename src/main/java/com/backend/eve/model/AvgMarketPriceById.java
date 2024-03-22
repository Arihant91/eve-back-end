package com.backend.eve.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AvgMarketPriceById {

    @JsonAlias("adjusted_price")
    private Long adjustedPrice;
    @JsonAlias("average_price")
    private Long averagePrice;
    private int typeId;
}
