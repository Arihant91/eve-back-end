package com.backend.eve.service;

import com.backend.eve.client.EveMongoClient;
import com.backend.eve.model.GroupDetails;
import com.backend.eve.model.Item;
import com.backend.eve.model.MarketItem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class MongoService {
    private final EveMongoClient mongoClient;
    private final CodecRegistry pojoCodecRegistry;

    public MongoService(EveMongoClient mongoClient) {
        this.mongoClient = mongoClient;
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().register(GroupDetails.class, MarketItem.class).build()));

    }


    public List<GroupDetails> getMarketGroups() {
        MongoDatabase db = mongoClient.getMarketGroupsCollection().withCodecRegistry(pojoCodecRegistry);
        List<GroupDetails> groupDetailsList = new ArrayList<>();
        MongoCollection<GroupDetails> collection = db.getCollection("MarketStructure", GroupDetails.class);

        collection.find().into(groupDetailsList);
        return groupDetailsList;
    }

    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>(getMarketGroups().stream()
                .flatMap(group -> getMarketItems(group).stream())
                .toList());
        list.sort(Comparator.comparing(Item::name));
        return list;

    }

    private List<Item> getMarketItems(GroupDetails groupDetails) {
        List<Item> itemList = new ArrayList<>();
        if (isNullOrEmpty(groupDetails.getChildGroups())
                && !isNullOrEmpty(groupDetails.getMarketItems())) {
            itemList.addAll(groupDetails
                    .getMarketItems()
                    .stream()
                    .map(marketItem -> new Item(marketItem.getName(), marketItem.getTypeId()))
                    .toList());
        }
        if (isNullOrEmpty(groupDetails.getMarketItems())
                && !isNullOrEmpty(groupDetails.getChildGroups())) {
            groupDetails.getChildGroups().forEach(group ->
                    itemList.addAll(getMarketItems(group)));
        }
        return itemList;


    }

    private boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }


}
