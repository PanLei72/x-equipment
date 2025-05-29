package com.x.equipment.view.mobile.equipmentrepair;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;

import java.time.LocalDateTime;

@Route(value = "repair-orders-confirm/:id", layout = MobileMainView.class)
@ViewController(id = "EQUI_RepairOrder.confirm")
@ViewDescriptor(path = "repair-order-confirm-view.xml")
@EditedEntityContainer("repairOrderDc")
public class RepairOrderConfirmView extends StandardDetailView<RepairOrder> {
    @Subscribe("saveAction")
    public void onSaveAction(final ActionPerformedEvent event) {
        RepairOrder repairOrder = this.getEditedEntity();
        repairOrder.setOrderStatus(RepairOrderStatus.CLOSED);
        repairOrder.setCompleteRepairTime(LocalDateTime.now());

        closeWithSave();
    }


}