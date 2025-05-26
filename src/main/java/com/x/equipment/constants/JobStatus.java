package com.x.equipment.constants;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum JobStatus implements EnumClass<String> {

    NEW("New"),
    CANCEL("Cancel"),
    DONE("Done");

    private final String id;

    JobStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static JobStatus fromId(String id) {
        for (JobStatus at : JobStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}