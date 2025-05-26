package com.x.equipment.view.repairorder;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "repair-orders/:id", layout = MainView.class)
@ViewController(id = "EQUI_RepairOrder.detail")
@ViewDescriptor(path = "repair-order-detail-view.xml")
@EditedEntityContainer("repairOrderDc")
@DialogMode(width = "64em")
public class RepairOrderDetailView extends StandardDetailView<RepairOrder> {
}