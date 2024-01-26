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
        return sortMarketItems();
    }

    private MarketGroups sortMarketItems() {
        List<Integer> marketGroupsId = getMarket();
        Map<Integer, GroupDetails> mappedGroups = new HashMap<>();
        for (int id : marketGroupsId) {
            GroupDetails groupDetails = getGroupDetailsResponse(getItemById(id));
            mappedGroups.put(groupDetails.getMarketGroupId(), groupDetails);
        }
        for(GroupDetails groupDetails: mappedGroups.values()){
            Integer parentGroupId = groupDetails.getParentGroupId();
            if(parentGroupId != null){
                mappedGroups.get(parentGroupId).getChildGroupDetailsList().add(groupDetails);
            }
        }
        MarketGroups marketGroups = new MarketGroups();
        for(GroupDetails groupDetails: mappedGroups.values()){
            if(groupDetails.getParentGroupId() == null) {
                marketGroups.getItems().add(groupDetails);
            }
        }
        return marketGroups;
    }

    private GroupDetails getGroupDetailsResponse(GroupDetailsResponse resp) {
        List<MarketItem> marketItemList = new ArrayList<>();
        for (int id : resp.getTypes()) {
            marketItemList.add(getItemType(id));
        }
        return new GroupDetails(resp.getDescription(), resp.getParentGroupId(), resp.getMarketGroupId(), resp.getName(), marketItemList);
    }
}
