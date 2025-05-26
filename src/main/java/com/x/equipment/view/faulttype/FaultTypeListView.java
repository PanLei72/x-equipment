package com.x.equipment.view.faulttype;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.FaultType;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;


@Route(value = "fault-types", layout = MainView.class)
@ViewController(id = "EQUI_FaultType.list")
@ViewDescriptor(path = "fault-type-list-view.xml")
@LookupComponent("faultTypesDataGrid")
@DialogMode(width = "64em")
public class FaultTypeListView extends StandardListView<FaultType> {
}