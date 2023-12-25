package com.backend.eve.service;

import com.backend.eve.client.EveMongoClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MongoService {
    private EveMongoClient mongoClient;

    public void readMarketGroups(){
        mongoClient.getMarketGroupsCollection();
    }
}
