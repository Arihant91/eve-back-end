package com.backend.eve.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketItem {
    private Float capacity;
    private String description;
    private String name;
    @BsonProperty("group_id")
    private Integer groupId;
    @BsonProperty("market_group_id")
    private Integer marketGroupId;
    @BsonProperty("type_id")
    private Integer typeId;

}
