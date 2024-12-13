package com.backend.eve.repository;

import com.backend.eve.entity.StructuresByRegionEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface StructuresByRegionRepository extends CassandraRepository<StructuresByRegionEntity, Long> {

    @Query(value= "SELECT * FROM orders.structures_by_region WHERE region_id=?0" )
    StructuresByRegionEntity getStructuresInRegion(Long regionId);
}
