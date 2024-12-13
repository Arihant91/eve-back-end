package com.backend.eve.client;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class EveMongoClient {

    private String url = "mongodb://localhost:27017";
    private MongoClient mongoClient = MongoClients.create(url);

    public MongoDatabase getMarketGroupsCollection() {
        return mongoClient.getDatabase("EveMarket");
    }
}
