package com.x.equipment.view.mobile.equipmentrepair;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.mobile.main.MobileMainView;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;

import java.time.LocalDateTime;

@Route(value = "repair-order-cancel/:id", layout = MobileMainView.class)
@ViewController(id = "EQUI_RepairOrder.cancel")
@ViewDescriptor(path = "repair-order-cancel-view.xml")
@EditedEntityContainer("repairOrderDc")
public class RepairOrderCancelView extends StandardDetailView<RepairOrder> {

    @Subscribe("saveAction")
    public void onSaveAction(final ActionPerformedEvent event) {
        RepairOrder repairOrder = this.getEditedEntity();
        repairOrder.setOrderStatus(RepairOrderStatus.CANCELED);
        repairOrder.setCompleteRepairTime(LocalDateTime.now());

        closeWithSave();
    }

}