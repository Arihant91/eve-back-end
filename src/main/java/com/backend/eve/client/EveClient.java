package com.backend.eve.client;

import com.backend.eve.model.AvgMarketPriceById;
import com.backend.eve.model.GroupDetailsResponse;
import com.backend.eve.model.MarketItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "EveBackend", url = "${eve.url}")
public interface EveClient {

    @GetMapping(value = "/latest/markets/groups/?datasource=tranquility")
    List<Integer> getMarketGroupsID();

    @GetMapping(value = "/latest/markets/groups/{id}")
    GroupDetailsResponse getItemByID(@PathVariable("id") int id);

    @GetMapping(value = "/latest/markets/prices/?datasource=tranquility")
    List<AvgMarketPriceById> getAvarageMarketPrices();

    @GetMapping(value = "/latest/universe/types/{type}")
    MarketItem getItemType(@PathVariable("type") int type);

}
