package com.backend.eve.repository;

import com.backend.eve.entity.OrdersStatsByLocationEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersStatsByLocationRepository extends CassandraRepository<OrdersStatsByLocationEntity, Long> {

    @Query(value = "SELECT * FROM orders.orders_stats_by_location WHERE location_id=?0 AND type_id=?1 AND time_of_scraping > ?2 AND time_of_scraping < ?3 AND is_buy_orders = ?4")
    List<OrdersStatsByLocationEntity> getOrderStatsByLocation(Long locationId, Long typeId, LocalDateTime startDate, LocalDateTime endDate, Boolean isBuyOrder);
}
