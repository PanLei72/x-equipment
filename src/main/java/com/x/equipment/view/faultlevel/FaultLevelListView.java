package com.x.equipment.view.faultlevel;

import com.x.equipment.entity.FaultLevel;

import com.x.equipment.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "fault-levels", layout = MainView.class)
@ViewController(id = "EQUI_FaultLevel.list")
@ViewDescriptor(path = "fault-level-list-view.xml")
@LookupComponent("faultLevelsDataGrid")
@DialogMode(width = "64em")
public class FaultLevelListView extends StandardListView<FaultLevel> {
}