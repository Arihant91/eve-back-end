package com.backend.eve.model;

import lombok.Builder;

import java.util.List;

@Builder
public record SortedByCharHash(String key, List<Item> items) {
}
