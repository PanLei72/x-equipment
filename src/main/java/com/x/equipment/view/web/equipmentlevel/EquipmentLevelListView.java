package com.x.equipment.view.web.equipmentlevel;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.x.equipment.entity.EquipmentLevel;
import com.x.equipment.service.EquipmentLevelService;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "equipment-levels", layout = MainView.class)
@ViewController(id = "EQUI_EquipmentLevel.list")
@ViewDescriptor(path = "equipment-level-list-view.xml")
@LookupComponent("equipmentLevelsDataGrid")
public class EquipmentLevelListView extends StandardListView<EquipmentLevel> {

    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @Autowired
    private EquipmentLevelService equipmentLevelService;

    @Install(to = "equipmentLevelsDataGrid.createAction", subject = "initializer")
    private void equipmentLevelsDataGridCreateActionInitializer(final EquipmentLevel equipmentLevel) {
        equipmentLevel.setLevelName(equipmentLevelService.generateName());
    }

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