package com.backend.eve.model;

import lombok.Builder;

@Builder
public record Item(String name, Integer id) {
}
