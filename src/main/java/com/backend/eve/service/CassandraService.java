package com.backend.eve.service;

import com.backend.eve.entity.OrdersStatsByLocationEntity;
import com.backend.eve.entity.OrdersStatsByRegionEntity;
import com.backend.eve.model.OrdersStatsByLocation;
import com.backend.eve.model.OrdersStatsByRegion;
import com.backend.eve.model.StructuresByRegion;
import com.backend.eve.repository.OrdersStatsByLocationRepository;
import com.backend.eve.repository.OrdersStatsByRegionRepository;
import com.backend.eve.repository.StructuresByRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CassandraService {
    private final OrdersStatsByRegionRepository ordersStatsByRegionRepository;
    private final OrdersStatsByLocationRepository ordersStatsByLocationRepository;
    private final StructuresByRegionRepository structuresByRegionRepository;

    public StructuresByRegion getStructuresByRegion(Long regionId) {
        var structures = structuresByRegionRepository.getStructuresInRegion(regionId);
        return new StructuresByRegion(structures.getRegionId(), structures.getStructures());
    }

    public List<OrdersStatsByRegion> getOrdersByRegion(List<Long> regionIds, List<Long> typeIds, LocalDateTime startDate, LocalDateTime endDate, Boolean isBuyOrder) {
        List<OrdersStatsByRegionEntity> ordersStatsByRegionEntities = Collections.synchronizedList(new ArrayList<>());
        if (regionIds.size() > 1 && typeIds.size() > 1) {
            return List.of(OrdersStatsByRegion.builder().build());
        } else {
            regionIds.parallelStream().forEach(regId ->
                    typeIds.parallelStream().forEach(tyId -> {
                        ordersStatsByRegionEntities.addAll(ordersStatsByRegionRepository.getOrdersStatsByRegion(regId, tyId, startDate, endDate, isBuyOrder));
                    })
            );
        }

        List<OrdersStatsByRegion> ordersStatsByRegionList = new ArrayList<>();
        ordersStatsByRegionEntities.forEach(ordersStatsByRegionEntity -> {
            ordersStatsByRegionList.add(
                    OrdersStatsByRegion.builder()
                            .regionId(ordersStatsByRegionEntity.getRegionId())
                            .typeId(ordersStatsByRegionEntity.getTypeId())
                            .volumeRemain(ordersStatsByRegionEntity.getVolumeRemain())
                            .orderCount(ordersStatsByRegionEntity.getOrderCount())
                            .avgPrice(ordersStatsByRegionEntity.getAvgPrice())
                            .isBuyOrders(ordersStatsByRegionEntity.getIsBuyOrders())
                            .lowestPrice(ordersStatsByRegionEntity.getLowestPrice())
                            .highestPrice(ordersStatsByRegionEntity.getHighestPrice())
                            .timeOfScraping(formatTime(ordersStatsByRegionEntity.getTimeOfScraping()))
                            .medianPrice(ordersStatsByRegionEntity.getMedianPrice())
                            .stdDeviation(ordersStatsByRegionEntity.getStdDeviation())
                            .build());
        });
        return ordersStatsByRegionList;
    }

    private LocalDateTime formatTime(LocalDateTime timeOfScraping) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");
        return LocalDateTime.parse(timeOfScraping.format(formatter), formatter);
    }

    public List<OrdersStatsByLocation> getOrdersByLocation(List<Long> locationIds, List<Long> typeIds, LocalDateTime startDate, LocalDateTime endDate, Boolean isBuyOrder) {
        List<OrdersStatsByLocationEntity> entityList = new ArrayList<>();
        if (locationIds.size() > 1 && typeIds.size() > 1) {
            return List.of(OrdersStatsByLocation.builder().build());
        } else {
            locationIds.forEach(locationId -> {
                typeIds.forEach(typeId -> {
                    entityList.addAll(ordersStatsByLocationRepository.getOrderStatsByLocation(locationId, typeId, startDate, endDate, isBuyOrder));
                });
            });
        }
        return entityList.stream().map(ordersStatsByLocationEntity -> OrdersStatsByLocation
                        .builder()
                        .regionId(ordersStatsByLocationEntity.getRegionId())
                        .locationId(ordersStatsByLocationEntity.getLocationId())
                        .typeId(ordersStatsByLocationEntity.getTypeId())
                        .volumeRemain(ordersStatsByLocationEntity.getVolumeRemain())
                        .orderCount(ordersStatsByLocationEntity.getOrderCount())
                        .avgPrice(ordersStatsByLocationEntity.getAvgPrice())
                        .isBuyOrders(ordersStatsByLocationEntity.getIsBuyOrders())
                        .lowestPrice(ordersStatsByLocationEntity.getLowestPrice())
                        .highestPrice(ordersStatsByLocationEntity.getHighestPrice())
                        .timeOfScraping(ordersStatsByLocationEntity.getTimeOfScraping())
                        .medianPrice(ordersStatsByLocationEntity.getMedianPrice())
                        .stdDeviation(ordersStatsByLocationEntity.getStdDeviation())
                        .build())
                .toList();

    }
}
