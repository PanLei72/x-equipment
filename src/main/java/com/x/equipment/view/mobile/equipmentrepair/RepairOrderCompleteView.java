package com.x.equipment.view.mobile.equipmentrepair;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;

import java.time.LocalDateTime;

@Route(value = "repair-orders-complete/:id", layout = MobileMainView.class)
@ViewController(id = "EQUI_RepairOrder.complete")
@ViewDescriptor(path = "repair-order-complete-view.xml")
@EditedEntityContainer("repairOrderDc")
public class RepairOrderCompleteView extends StandardDetailView<RepairOrder> {
    @Subscribe("saveAction")
    public void onSaveAction(final ActionPerformedEvent event) {
        RepairOrder repairOrder = this.getEditedEntity();
        repairOrder.setOrderStatus(RepairOrderStatus.COMPLETED);
        repairOrder.setCompleteRepairTime(LocalDateTime.now());

        closeWithSave();
    }


}