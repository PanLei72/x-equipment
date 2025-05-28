package com.x.equipment.view.repairorder;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;

import java.time.LocalDateTime;


@Route(value = "repair-orders", layout = MainView.class)
@ViewController(id = "EQUI_RepairOrder.list")
@ViewDescriptor(path = "repair-order-list-view.xml")
@LookupComponent("repairOrdersDataGrid")
@DialogMode(width = "64em")
public class RepairOrderListView extends StandardListView<RepairOrder> {
}