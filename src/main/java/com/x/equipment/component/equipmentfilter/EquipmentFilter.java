package com.x.equipment.component.equipmentfilter;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.x.equipment.entity.Equipment;
import io.jmix.core.Messages;
import io.jmix.flowui.UiComponents;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class EquipmentFilter extends Composite<Details> implements InitializingBean {

    private FormLayout layout;
    private TextField nameFilter;
    private TextField descriptionFilter;
    protected Messages messages;
    protected UiComponents uiComponents;

    @Autowired
    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    @Autowired
    public void setUiComponents(UiComponents uiComponents) {
        this.uiComponents = uiComponents;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initNameFilter();
        initDescriptionFilter();
        initLayout();
        initRootComponent();
    }

    protected void initNameFilter() {
        nameFilter = uiComponents.create(TextField.class);
        nameFilter.setLabel(messages.getMessage(Equipment.class, "Equipment.equipmentName"));
        nameFilter.addValueChangeListener(this::onFilterFieldValueChange);
    }

    protected void initDescriptionFilter() {
        descriptionFilter = uiComponents.create(TextField.class);
        descriptionFilter.setLabel(messages.getMessage(Equipment.class, "Equipment.description"));
        descriptionFilter.addValueChangeListener(this::onFilterFieldValueChange);
    }


    protected void initLayout() {
        layout = uiComponents.create(FormLayout.class);
        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("50em", 3)
        );

        layout.add(nameFilter, descriptionFilter);
    }

    protected void initRootComponent() {
        getContent().setSummaryText(messages.getMessage(EquipmentFilter.class, "summaryText"));
        getContent().setWidthFull();
        getContent().addContent(layout);
    }

    public boolean isNameFilterVisible() {
        return nameFilter.isVisible();
    }

    public void setNameFilterVisible(boolean visible) {
        nameFilter.setVisible(visible);
    }

    public boolean isDescriptionFilterVisible() {
        return nameFilter.isVisible();
    }

    public void setDescriptionFilterVisible(boolean visible) {
        descriptionFilter.setVisible(visible);
    }


    protected void onFilterFieldValueChange(AbstractField.ComponentValueChangeEvent<?, ?> event) {
        EquipmentFilterChangeEvent filterChangeEvent = new EquipmentFilterChangeEvent(this,
                nameFilter.getValue(), descriptionFilter.getValue());
        getEventBus().fireEvent(filterChangeEvent);
    }

    public Registration addEquipmentFilterChangeListener(ComponentEventListener<EquipmentFilterChangeEvent> listener) {
        return getEventBus().addListener(EquipmentFilterChangeEvent.class, listener);
    }
}
