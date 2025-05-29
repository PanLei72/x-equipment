package com.x.equipment.view.web.faulttype;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.FaultType;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "fault-types/:id", layout = MainView.class)
@ViewController(id = "EQUI_FaultType.detail")
@ViewDescriptor(path = "fault-type-detail-view.xml")
@EditedEntityContainer("faultTypeDc")
public class FaultTypeDetailView extends StandardDetailView<FaultType> {
}