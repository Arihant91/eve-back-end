package com.backend.eve.service;

import com.backend.eve.client.EveMongoClient;
import com.backend.eve.model.MarketGroups;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class MongoService {
    private final EveMongoClient mongoClient;
    private final CodecProvider pojoCodecProvider;
    private final CodecRegistry pojoCodecRegistry;

    public MongoService(EveMongoClient mongoClient) {
        this.mongoClient = mongoClient;
        pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    }


    public MarketGroups readMarketGroups(){
        MongoDatabase db =mongoClient.getMarketGroupsCollection().withCodecRegistry(pojoCodecRegistry);
        List<MarketGroups> marketGroups = new ArrayList<>();
        MongoCollection<MarketGroups> collection = db.getCollection("MarketGroups", MarketGroups.class);
        collection.find().into(marketGroups);
        return marketGroups.getFirst();
    }

    public void writeMarketGroups(MarketGroups marketGroups){
        MongoDatabase db =mongoClient.getMarketGroupsCollection().withCodecRegistry(pojoCodecRegistry);
        MongoCollection<MarketGroups> collection = db.getCollection("MarketGroups", MarketGroups.class);
        collection.insertOne(marketGroups);
    }
}
