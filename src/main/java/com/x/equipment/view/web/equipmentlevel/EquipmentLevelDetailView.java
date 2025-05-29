package com.x.equipment.view.web.equipmentlevel;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.EquipmentLevel;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "equipment-levels/:id", layout = MainView.class)
@ViewController(id = "EQUI_EquipmentLevel.detail")
@ViewDescriptor(path = "equipment-level-detail-view.xml")
@EditedEntityContainer("equipmentLevelDc")
public class EquipmentLevelDetailView extends StandardDetailView<EquipmentLevel> {
}