package com.x.equipment.view.repairorder;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;


@Route(value = "repair-orders", layout = MainView.class)
@ViewController(id = "EQUI_RepairOrder.list")
@ViewDescriptor(path = "repair-order-list-view.xml")
@LookupComponent("repairOrdersDataGrid")
public class RepairOrderListView extends StandardListView<RepairOrder> {
}