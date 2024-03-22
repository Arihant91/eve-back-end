package com.backend.eve.client;

import com.backend.eve.model.Entity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(value = "EveBackend", url = "${eve.url}")
public interface EveClient {
    @GetMapping(value = "/latest/universe/regions" )
    List<Integer> getRegionIds();

    @PostMapping(value = "/latest/universe/names")
    List<Entity> getNamesById(List<Integer> ids);

}
