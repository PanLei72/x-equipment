package com.x.equipment.constants;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CheckCycleUnit implements EnumClass<String> {

    DAY("Day"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year");

    private final String id;

    CheckCycleUnit(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CheckCycleUnit fromId(String id) {
        for (CheckCycleUnit at : CheckCycleUnit.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}