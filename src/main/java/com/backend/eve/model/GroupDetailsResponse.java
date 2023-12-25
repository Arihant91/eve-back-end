package com.backend.eve.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupDetailsResponse {

    private String description;
    @JsonAlias("market_group_id")
    private Integer marketGroupId;
    @JsonAlias("parent_group_id")
    private Integer parentGroupId;
    private String name;
    private List<Integer> types;
}
