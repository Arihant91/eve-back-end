package com.backend.eve.entity;

import com.backend.eve.model.Structures;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Builder
@Getter
@Table("structures_by_region_vt_structures")
public class StructuresByRegionEntity {

    @PrimaryKeyColumn(name = "region_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    Long regionId;

    List<Structures> structures;
}
