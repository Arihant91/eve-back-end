package com.backend.eve.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
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

    public MarketItem(Float capacity, String itemA) {
    }

    public MarketItem(int i, String itemA, int i1) {
    }
}
