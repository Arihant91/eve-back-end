package com.backend.eve.service;

import com.backend.eve.entity.OrdersStatsByLocationEntity;
import com.backend.eve.entity.OrdersStatsByRegionEntity;
import com.backend.eve.entity.StructuresByRegionEntity;
import com.backend.eve.model.Structures;
import com.backend.eve.repository.OrdersStatsByLocationRepository;
import com.backend.eve.repository.OrdersStatsByRegionRepository;
import com.backend.eve.repository.StructuresByRegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CassandraServiceTest {

    @Mock
    private OrdersStatsByRegionRepository ordersStatsByRegionRepository;

    @Mock
    private OrdersStatsByLocationRepository ordersStatsByLocationRepository;

    @Mock
    private StructuresByRegionRepository structuresByRegionRepository;

    private CassandraService cassandraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cassandraService = new CassandraService(ordersStatsByRegionRepository, ordersStatsByLocationRepository, structuresByRegionRepository);
    }

    @Test
    void testGetStructuresByRegion() {
        Long regionId = 1L;
        List<Structures> mockStructuresList = List.of(new Structures(111, "Structure 1"), new Structures(112, "Structure 2"));
        var mockStructures = StructuresByRegionEntity.builder().regionId(regionId).structures(mockStructuresList).build();

        when(structuresByRegionRepository.getStructuresInRegion(regionId)).thenReturn(mockStructures);

        var result = cassandraService.getStructuresByRegion(regionId);

        assertNotNull(result);
        assertEquals(regionId, result.regionId());
        assertEquals(mockStructuresList, result.structures());
        verify(structuresByRegionRepository, times(1)).getStructuresInRegion(regionId);
    }

    @Test
    void testGetOrdersByRegionMultipleRegionsAndTypes() {
        List<Long> regionIds = List.of(1L, 2L);
        List<Long> typeIds = List.of(10L, 20L);
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        var result = cassandraService.getOrdersByRegion(regionIds, typeIds, startDate, endDate, true);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetOrdersByRegionSingleRegionAndType() {
        List<Long> regionIds = List.of(1L);
        List<Long> typeIds = List.of(10L);
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        var mockEntity = OrdersStatsByRegionEntity.builder()
                .regionId(1L)
                .typeId(10L)
                .timeOfScraping(startDate)
                .isBuyOrders(true)
                .avgPrice(BigDecimal.valueOf(50.0))
                .medianPrice(BigDecimal.valueOf(50.0))
                .volumeRemain(100L)
                .highestPrice(BigDecimal.valueOf(55.0))
                .lowestPrice(BigDecimal.valueOf(45.0))
                .orderCount(5)
                .stdDeviation(BigDecimal.valueOf(5.0))
                .build();

        when(ordersStatsByRegionRepository.getOrdersStatsByRegion(1L, 10L, startDate, endDate, true)).thenReturn(List.of(mockEntity));

        var result = cassandraService.getOrdersByRegion(regionIds, typeIds, startDate, endDate, true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getRegionId());
        assertEquals(10L, result.get(0).getTypeId());
        verify(ordersStatsByRegionRepository, times(1)).getOrdersStatsByRegion(1L, 10L, startDate, endDate, true);
    }

    @Test
    void testGetOrdersByLocationMultipleLocationsAndTypes() {
        List<Long> locationIds = List.of(1L, 2L);
        List<Long> typeIds = List.of(10L, 20L);
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        var result = cassandraService.getOrdersByLocation(locationIds, typeIds, startDate, endDate, true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getLocationId());
    }

    @Test
    void testGetOrdersByLocationSingleLocationAndType() {
        List<Long> locationIds = List.of(1L);
        List<Long> typeIds = List.of(10L);
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        var mockEntity = OrdersStatsByLocationEntity.builder()
                .locationId(1L)
                .regionId(1L)
                .typeId(10L)
                .timeOfScraping(startDate)
                .isBuyOrders(true)
                .avgPrice(BigDecimal.valueOf(50.0))
                .medianPrice(BigDecimal.valueOf(50.0))
                .volumeRemain(100L)
                .highestPrice(BigDecimal.valueOf(55.0))
                .lowestPrice(BigDecimal.valueOf(45.0))
                .orderCount(5)
                .stdDeviation(BigDecimal.valueOf(5.0))
                .build();

        when(ordersStatsByLocationRepository.getOrderStatsByLocation(1L, 10L, startDate, endDate, true)).thenReturn(List.of(mockEntity));

        var result = cassandraService.getOrdersByLocation(locationIds, typeIds, startDate, endDate, true);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getLocationId());
        assertEquals(10L, result.get(0).getTypeId());
        verify(ordersStatsByLocationRepository, times(1)).getOrderStatsByLocation(1L, 10L, startDate, endDate, true);
    }
}
