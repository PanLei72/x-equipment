package com.x.equipment.view.equipmentclass;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.dom.Element;
import com.x.equipment.entity.EquipmentClass;

import com.x.equipment.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "equipmentClasses", layout = MainView.class)
@ViewController("EQUI_EquipmentClass.list")
@ViewDescriptor("equipment-class-list-view.xml")
@LookupComponent("equipmentClassesDataGrid")
@DialogMode(width = "64em")
public class EquipmentClassListView extends StandardListView<EquipmentClass> {
    @ViewComponent
    private HorizontalLayout buttonsPanel;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        Element parentElement = getElement().getParent();
        if(parentElement != null && parentElement.getAttribute("class") != null) {
            if (parentElement.getAttribute("class").contains("jmix-dialog")) {
                buttonsPanel.setVisible(false);
            }
        }
    }

}