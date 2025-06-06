package com.x.equipment.constants;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CheckCategory implements EnumClass<String> {

    INSPECTION("Inspection"),
    MAINTENANCE("Maintenance");

    private final String id;

    CheckCategory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CheckCategory fromId(String id) {
        for (CheckCategory at : CheckCategory.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}