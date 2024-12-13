package com.backend.eve.service;

import com.backend.eve.client.EveClient;
import com.backend.eve.model.Entity;
import com.backend.eve.model.GroupDetails;
import com.backend.eve.model.Item;
import com.backend.eve.model.MarketItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarketServiceTest {

    @Mock
    private EveClient eveClient;

    @Mock
    private MongoService mongoService;
    private MarketService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MarketService(eveClient, mongoService);
    }

    @Test
    void testGetMarketStructure() {
        List<MarketItem> marketItems = List.of(MarketItem.builder().marketGroupId(1).build(), MarketItem.builder().marketGroupId(2).build());
        GroupDetails childGroup = new GroupDetails("Child Group", 101, null, "Child", marketItems, null);
        GroupDetails parentGroup = new GroupDetails("Parent Group", 100, null, "Parent", marketItems, List.of(childGroup));

        when(mongoService.getMarketGroups()).thenReturn(List.of(parentGroup));

        var result = underTest.getMarketStructure();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Parent", result.get(0).getName());
        assertEquals(1, result.get(0).getChildGroups().size());
        assertEquals("Child", result.get(0).getChildGroups().get(0).getName());
        verify(mongoService, times(1)).getMarketGroups();
    }

    @Test
    void testGetRegions() {
        List<Entity> mockRegionEntities = List.of(
                new Entity("Region", 2, "Region B"),
                new Entity("Region", 1, "Region A")
        );
        var listOfIds = new ArrayList<Integer>();
        listOfIds.add(1);
        listOfIds.add(2);
        when(eveClient.getRegionIds()).thenReturn(listOfIds);
        when(eveClient.getNamesById(anyList())).thenReturn(new ArrayList<>(mockRegionEntities));

        var result = underTest.getRegions();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Region A", result.get(0).getName());
        assertEquals("Region B", result.get(1).getName());
        verify(eveClient, times(1)).getRegionIds();
        verify(eveClient, times(1)).getNamesById(anyList());
    }

    @Test
    void testGetAllItems() {
        List<Item> mockItems = List.of(
                new Item("Apple", 1),
                new Item("Banana", 2),
                new Item("Apricot", 3),
                new Item("Blueberry", 4)
        );

        when(mongoService.getAllItems()).thenReturn(mockItems);

        var result = underTest.getAllItems();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("A"));
        assertTrue(result.containsKey("B"));

        var itemsStartingWithA = result.get("A");
        assertEquals(2, itemsStartingWithA.size());
        assertEquals("Apple", itemsStartingWithA.get(0).name());
        assertEquals("Apricot", itemsStartingWithA.get(1).name());

        var itemsStartingWithB = result.get("B");
        assertEquals(2, itemsStartingWithB.size());
        assertEquals("Banana", itemsStartingWithB.get(0).name());
        assertEquals("Blueberry", itemsStartingWithB.get(1).name());

        verify(mongoService, times(1)).getAllItems();
    }
}
