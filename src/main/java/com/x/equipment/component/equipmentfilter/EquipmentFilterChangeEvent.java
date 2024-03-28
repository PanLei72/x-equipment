package com.x.equipment.component.equipmentfilter;

import com.vaadin.flow.component.ComponentEvent;
import io.jmix.security.model.BaseRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

public class EquipmentFilterChangeEvent extends ComponentEvent<EquipmentFilter> {

    private String name;
    private String description;

    public EquipmentFilterChangeEvent(EquipmentFilter filter) {
        this(filter, null, null);
    }

    public EquipmentFilterChangeEvent(EquipmentFilter filter,
                                      @Nullable String name, @Nullable String description) {
        super(filter, true);

        this.name = name;
        this.description = description;
    }

    @Nullable
    public String getNameValue() {
        return name;
    }

    @Nullable
    public String getDescriptionValue() {
        return description;
    }


    @Nullable

    public boolean matches(BaseRole role) {
        return (name == null || StringUtils.containsIgnoreCase(role.getName(), name))
                && (description == null || StringUtils.containsIgnoreCase(role.getCode(), description));
    }
}
