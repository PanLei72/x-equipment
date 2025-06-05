package com.x.equipment.constants;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum RepairOrderStatus implements EnumClass<String> {

    CREATED("Created"),
    IN_PROGRESS("InProgress"),
    COMPLETED("Completed"),
    CANCELED("Canceled"),
    CLOSED("Closed");

    private final String id;

    RepairOrderStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static RepairOrderStatus fromId(String id) {
        for (RepairOrderStatus at : RepairOrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}