package com.backend.eve.service;

import com.backend.eve.client.EveClient;
import com.backend.eve.client.EveMongoClient;
import com.backend.eve.model.GroupDetails;
import com.backend.eve.model.GroupDetailsResponse;
import com.backend.eve.model.MarketItem;
import com.backend.eve.model.MarketGroups;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MarketService {

    private final EveClient eveClient;

    private final MongoService mongoService;

    private List<Integer> getMarket() {
        return eveClient.getMarketGroupsID();
    }

    private GroupDetailsResponse getItemById(int id) {
        return eveClient.getItemByID(id);
    }

    private MarketItem getItemType(int type) {
        return eveClient.getItemType(type);
    }

    public MarketGroups getMarketStructure() {
        mongoService.readMarketGroups();
        List<Integer> marketGroupsId = getMarket();
        MarketGroups marketGroups = new MarketGroups();
        for (int id: marketGroupsId) {
            marketGroups.getItems().add(getGroupDetails(getItemById(id)));
        }
        return marketGroups;
    }

    private GroupDetails getGroupDetails(GroupDetailsResponse groupDetailsResponse) {
        List<Integer> types = groupDetailsResponse.getTypes();
        List<MarketItem> marketItemList = new ArrayList<>();
        if (types != null) {
            types.forEach(type -> marketItemList.add(getItemType(type)));
        }
        return new GroupDetails(groupDetailsResponse.getDescription(),
                groupDetailsResponse.getParentGroupId(),
                groupDetailsResponse.getMarketGroupId(),
                groupDetailsResponse.getName(),
                marketItemList);
    }
}
