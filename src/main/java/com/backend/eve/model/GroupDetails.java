package com.backend.eve.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetails {
    private String description;
    private Integer parentGroupId;
    private Integer marketGroupId;
    private String name;

    private List<GroupDetails> childGroupDetailsList;
    private List<MarketItem> marketItemList;

    public GroupDetails(String description, Integer parentGroupId, Integer marketGroupId, String name, List<MarketItem> marketItemList) {
        this.description = description;
        this.parentGroupId = parentGroupId;
        this.marketGroupId = marketGroupId;
        this.name = name;
        this.childGroupDetailsList = new ArrayList<>();
        this.marketItemList = marketItemList;
    }
}
