package com.backend.eve.model;

import java.util.List;


public record StructuresByRegion(Long regionId, List<Structures> structures) {
}