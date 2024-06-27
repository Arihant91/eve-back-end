package com.backend.eve.repository;

import com.backend.eve.entity.OrderEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface OrdersRepository extends CassandraRepository<OrderEntity, Long> {
    @Query(value = "SELECT * FROM orders WHERE location_id=?0 AND type_id=?1 AND is_buy_orders=?2 ALLOW FILTERING")
    List<OrderEntity> getOrdersByType(Long locationId, Long typeId, Boolean isBuyOrder);
}
