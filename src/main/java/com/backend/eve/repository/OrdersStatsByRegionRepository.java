package com.backend.eve.repository;

import com.backend.eve.entity.OrdersStatsByRegionEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface OrdersStatsByRegionRepository extends CassandraRepository<OrdersStatsByRegionEntity, Long> {

    @Query(value= "SELECT * FROM orders.orders_stats_by_region WHERE region_id=?0 AND type_id=?1 AND time_of_scraping > ?2 AND time_of_scraping < ?3 AND is_buy_orders = ?4 ALLOW FILTERING" )
    List<OrdersStatsByRegionEntity> getOrdersStatsByRegion(Long regionId, Long typeId, LocalDateTime startDate, LocalDateTime endDate, Boolean isBuyOrder);

}
