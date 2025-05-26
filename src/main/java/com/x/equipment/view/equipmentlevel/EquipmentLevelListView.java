package com.x.equipment.view.equipmentlevel;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.EquipmentLevel;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;


@Route(value = "equipment-levels", layout = MainView.class)
@ViewController(id = "EQUI_EquipmentLevel.list")
@ViewDescriptor(path = "equipment-level-list-view.xml")
@LookupComponent("equipmentLevelsDataGrid")
@DialogMode(width = "64em")
public class EquipmentLevelListView extends StandardListView<EquipmentLevel> {
}