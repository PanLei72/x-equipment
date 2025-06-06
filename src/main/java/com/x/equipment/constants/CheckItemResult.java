package com.x.equipment.constants;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CheckItemResult implements EnumClass<String> {

    OK("OK"),
    NG("NG");

    private final String id;

    CheckItemResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CheckItemResult fromId(String id) {
        for (CheckItemResult at : CheckItemResult.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}