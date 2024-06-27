package com.backend.eve.service;

import com.backend.eve.entity.OrdersMeanEntity;
import com.backend.eve.model.OrdersMean;
import com.backend.eve.repository.OrdersMeanRepository;
import com.backend.eve.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CassandraService {
    private final OrdersRepository ordersRepository;

    private final OrdersMeanRepository ordersMeanRepository;


    @Autowired
    public CassandraService(OrdersRepository ordersRepository, OrdersMeanRepository ordersMeanRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersMeanRepository = ordersMeanRepository;
    }

    public List<OrdersMean> getRecords(Long regionId, Long typeId, LocalDateTime startDate, LocalDateTime endDate, Boolean isBuyOrder) {
        List<OrdersMeanEntity> ordersMeanEntityList = ordersMeanRepository.getOrderMeansByRegionTypeOrder(regionId, typeId, startDate, endDate, isBuyOrder);
        List<OrdersMean> ordersMeansList = new ArrayList<>();
        ordersMeanEntityList.forEach(ordersMeanEntity -> {
            ordersMeansList.add(OrdersMean.builder()
                    .regionId(ordersMeanEntity.getRegionId())
                    .typeId(ordersMeanEntity.getTypeId())
                    .volumeRemain(ordersMeanEntity.getVolumeRemain())
                    .orderCount(ordersMeanEntity.getOrderCount())
                    .avgPrice(ordersMeanEntity.getAvgPrice())
                    .isBuyOrders(ordersMeanEntity.getIsBuyOrders())
                    .lowestPrice(ordersMeanEntity.getLowestPrice())
                    .highestPrice(ordersMeanEntity.getHighestPrice())
                    .timeOfScraping(ordersMeanEntity.getTimeOfScraping())
                    .locationId(ordersMeanEntity.getLocationId())
                    .build());
        });
        return ordersMeansList;
    }
}
