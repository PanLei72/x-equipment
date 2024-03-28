package com.x.equipment.view.equipmentclassequipment;

import com.vaadin.flow.router.Route;
import com.x.equipment.component.equipmentfilter.EquipmentFilter;
import com.x.equipment.component.equipmentfilter.EquipmentFilterChangeEvent;
import com.x.equipment.entity.Equipment;
import com.x.equipment.entity.EquipmentClass;
import com.x.equipment.entity.EquipmentClassEquipment;
import com.x.equipment.view.main.MainView;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.securityflowui.component.rolefilter.RoleFilter;
import io.jmix.securityflowui.component.rolefilter.RoleFilterChangeEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "equipmentClassEquipments", layout = MainView.class)
@ViewController("EQUI_EquipmentClassEquipment.list")
@ViewDescriptor("equipment-class-equipment-list-view.xml")
@LookupComponent("equipmentClassEquipmentsDataGrid")
@DialogMode(width = "64em")
public class EquipmentClassEquipmentListView extends StandardListView<EquipmentClassEquipment> {
    @ViewComponent
    private CollectionContainer<EquipmentClassEquipment> equipmentClassEquipmentsDc;

    @Autowired
    private DataManager dataManager;

    private EquipmentClass equipmentClass;
    @Autowired
    private UiComponents uiComponents;

    public EquipmentClass getEquipmentClass() {
        return equipmentClass;
    }

    public void setEquipmentClass(EquipmentClass equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        initFilter();
    }

    private void initFilter() {
        EquipmentFilter filter = uiComponents.create(EquipmentFilter.class);
        filter.addEquipmentFilterChangeListener(this::onEquipmentFilterChange);

        getContent().addComponentAsFirst(filter);
    }

    private void onEquipmentFilterChange(EquipmentFilterChangeEvent event) {
        loadEquipmentClassEquipments(event);
    }


    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        this.loadEquipmentClassEquipments(null);
    }

    private void loadEquipmentClassEquipments(EquipmentFilterChangeEvent equipmentFilterChangeEvent) {
        List<Equipment> equipmentList = this.loadEquipments(equipmentClass, equipmentFilterChangeEvent);
        List<EquipmentClassEquipment> equipmentClassEquipments = new ArrayList<>();
        for (Equipment equipment : equipmentList) {
            EquipmentClassEquipment equipmentClassEquipment = dataManager.create(EquipmentClassEquipment.class);
            equipmentClassEquipment.setEquipmentClass(equipmentClass);
            equipmentClassEquipment.setEquipment(equipment);

            equipmentClassEquipments.add(equipmentClassEquipment);
        }
        equipmentClassEquipmentsDc.setItems(equipmentClassEquipments);
    }

    private List<Equipment> loadEquipments(EquipmentClass equipmentClass, EquipmentFilterChangeEvent equipmentFilterChangeEvent) {

        StringBuilder queryJpql = new StringBuilder("select e from EQUI_Equipment e " +
                " where e.id not in (select ec.equipment.id from EQUI_EquipmentClassEquipment ec where" +
                " ec.equipmentClass = :equipmentClass ) ");
        String equipmentName = null;
        String description = null;
        if (equipmentFilterChangeEvent != null) {
            equipmentName = equipmentFilterChangeEvent.getNameValue();
            if (StringUtils.isNotBlank(equipmentName)) {
                queryJpql.append(" and e.equipmentName = :equipmentName");
            }
            description = equipmentFilterChangeEvent.getDescriptionValue();
            if (StringUtils.isNotBlank(description)) {
                queryJpql.append(" and e.description = :description");
            }
        }
        queryJpql.append(" order by e.equipmentName");
        FluentLoader.ByQuery<Equipment> equipmentByQuery = dataManager.load(Equipment.class)
                .query(queryJpql.toString());
        equipmentByQuery.parameter("equipmentClass", equipmentClass);

        if (StringUtils.isNotBlank(equipmentName)) {
            equipmentByQuery.parameter("equipmentName", equipmentName);
        }
        if (StringUtils.isNotBlank(description)) {
            equipmentByQuery.parameter("description", description);
        }

        return equipmentByQuery.list();
    }

}