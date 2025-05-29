package com.x.equipment.view.web.equipment;

import com.x.equipment.entity.Equipment;

import com.x.equipment.view.web.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "equipments/:id", layout = MainView.class)
@ViewController("EQUI_Equipment.detail")
@ViewDescriptor("equipment-detail-view.xml")
@EditedEntityContainer("equipmentDc")
public class EquipmentDetailView extends StandardDetailView<Equipment> {
}