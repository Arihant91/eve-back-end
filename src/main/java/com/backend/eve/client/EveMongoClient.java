package com.backend.eve.client;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class EveMongoClient {
    private MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    public MongoCollection<Document> getMarketGroupsCollection() {
        return mongoClient.getDatabase("MarketGroups").getCollection("Dont_delete");
    }
}
