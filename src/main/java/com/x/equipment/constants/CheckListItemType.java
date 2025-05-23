package com.x.equipment.constants;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CheckListItemType implements EnumClass<String> {

    BOOLEAN("Boolean"),
    STRING("String"),
    NUMBER("Number"),
    LIST("List");

    private final String id;

    CheckListItemType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CheckListItemType fromId(String id) {
        for (CheckListItemType at : CheckListItemType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}