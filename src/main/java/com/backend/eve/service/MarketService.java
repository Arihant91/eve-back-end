package com.backend.eve.service;

import com.backend.eve.client.EveClient;
import com.backend.eve.model.Entity;
import com.backend.eve.model.GroupDetails;
import com.backend.eve.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class MarketService {

    private final EveClient eveClient;

    private final MongoService mongoService;


    public List<GroupDetails> getMarketStructure(){
           return mongoService.getMarketGroups();
    }

    public List<Entity> getRegions(){
        List<Entity> regionEntityList = eveClient.getNamesById(eveClient.getRegionIds());
        regionEntityList.sort(Comparator.comparing(Entity::getName));
        return  regionEntityList;
    }

    public Map<String, List<Item>> getAllItems() {
        List<Item> items = mongoService.getAllItems();
        Map<String, List<Item>> mappedItems = new HashMap<>();
        items.forEach(item -> {
            String firstChar = item.name().trim().substring(0,1);
            if(mappedItems.containsKey(firstChar)){
                mappedItems.get(firstChar).add(item);
            } else {
                var itemList = new ArrayList<Item>();
                itemList.add(item);
                mappedItems.put(firstChar, itemList);
            }
        });
        return mappedItems;
    }





}
