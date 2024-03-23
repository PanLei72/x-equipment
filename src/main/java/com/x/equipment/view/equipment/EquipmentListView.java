package com.x.equipment.view.equipment;

import com.x.equipment.entity.Equipment;

import com.x.equipment.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "equipments", layout = MainView.class)
@ViewController("EQUI_Equipment.list")
@ViewDescriptor("equipment-list-view.xml")
@LookupComponent("equipmentsDataGrid")
@DialogMode(width = "64em")
public class EquipmentListView extends StandardListView<Equipment> {
}