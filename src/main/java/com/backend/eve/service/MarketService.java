package com.backend.eve.service;

import com.backend.eve.client.EveClient;
import com.backend.eve.model.Entity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class MarketService {

    private final EveClient eveClient;

    private final MongoService mongoService;

    private final ObjectMapper objectMapper;

    public String getMarketStructure(){
        String response = null;
        try{
            response = objectMapper.writeValueAsString(mongoService.getMarketGroups());
        }catch (JsonProcessingException e){
            System.out.println(e.toString());
        }

        return response;
    }

    public List<Entity> getRegions(){
        return  eveClient.getNamesById(eveClient.getRegionIds());
    }



}
