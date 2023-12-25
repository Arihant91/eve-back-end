package com.backend.eve.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class GroupDetails {
    private String description;
    private Integer parentGroupId;
    private Integer marketGroupId;
    private String name;

    private List<MarketItem> marketItemList;

}
