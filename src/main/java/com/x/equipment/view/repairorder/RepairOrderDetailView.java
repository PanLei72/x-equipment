package com.x.equipment.view.repairorder;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;

import java.time.LocalDateTime;

@Route(value = "repair-orders/:id", layout = MainView.class)
@ViewController(id = "EQUI_RepairOrder.detail")
@ViewDescriptor(path = "repair-order-detail-view.xml")
@EditedEntityContainer("repairOrderDc")
public class RepairOrderDetailView extends StandardDetailView<RepairOrder> {

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        RepairOrder repairOrder = this.getEditedEntity();
        repairOrder.setRepairTime(LocalDateTime.now());
        repairOrder.setOrderStatus(RepairOrderStatus.CREATED);
    }


}