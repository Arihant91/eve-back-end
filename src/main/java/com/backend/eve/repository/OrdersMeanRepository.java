package com.backend.eve.repository;

import com.backend.eve.entity.OrdersMeanEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersMeanRepository extends CassandraRepository<OrdersMeanEntity, Long> {

    @Query(value= "SELECT * FROM orders_mean_by_region_type WHERE region_id=?0 AND type_id=?1 AND time_of_scraping > ?2 AND time_of_scraping < ?3 AND is_buy_orders = ?4 ALLOW FILTERING" )
    List<OrdersMeanEntity> getOrderMeansByRegionTypeOrder(Long regionId, Long typeId, LocalDateTime startDate, LocalDateTime endDate, Boolean isBuyOrder);
}
