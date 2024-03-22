package com.backend.eve.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDetails {
    private String description;
    @BsonProperty("market_group_id")
    private Integer marketGroupId;
    @BsonProperty("parent_group_id")
    private Integer parentGroupId;
    private String name;
    @BsonProperty("types")
    private List<MarketItem> marketItems;
    @BsonProperty("child_groups")
    private List<GroupDetails> childGroups;
}
