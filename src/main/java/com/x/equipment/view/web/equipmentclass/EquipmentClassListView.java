package com.x.equipment.view.web.equipmentclass;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import com.x.equipment.entity.EquipmentClass;

import com.x.equipment.service.EquipmentClassService;
import com.x.equipment.view.web.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "equipmentClasses", layout = MainView.class)
@ViewController("EQUI_EquipmentClass.list")
@ViewDescriptor("equipment-class-list-view.xml")
@LookupComponent("equipmentClassesDataGrid")
public class EquipmentClassListView extends StandardListView<EquipmentClass> {

    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @Autowired
    private EquipmentClassService equipmentClassService;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        Element parentElement = getElement().getParent();
        if(parentElement != null && parentElement.getAttribute("class") != null) {
            if (parentElement.getAttribute("class").contains("jmix-dialog")) {
                buttonsPanel.setVisible(false);
            }
        }
    }

    @Install(to = "equipmentClassesDataGrid.create", subject = "initializer")
    private void equipmentClassesDataGridCreateInitializer(final EquipmentClass equipmentClass) {
        equipmentClass.setEquipmentClassName(equipmentClassService.generateName());
    }
}