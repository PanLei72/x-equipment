package com.x.equipment.view.faultlevel;

import com.x.equipment.entity.FaultLevel;

import com.x.equipment.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "fault-levels/:id", layout = MainView.class)
@ViewController(id = "EQUI_FaultLevel.detail")
@ViewDescriptor(path = "fault-level-detail-view.xml")
@EditedEntityContainer("faultLevelDc")
@DialogMode(width = "64em")
public class FaultLevelDetailView extends StandardDetailView<FaultLevel> {
}