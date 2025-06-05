package com.x.equipment.view.mobile.equipmentrepair;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.service.RepairOrderService;
import com.x.equipment.view.mobile.main.MobileMainView;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Route(value = "repair-order-create/:id", layout = MobileMainView.class)
@ViewController(id = "EQUI_RepairOrder.create")
@ViewDescriptor(path = "repair-order-create-view.xml")
@EditedEntityContainer("repairOrderDc")
public class RepairOrderCreateView extends StandardDetailView<RepairOrder> {
    @Autowired
    private RepairOrderService repairOrderService;

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        RepairOrder repairOrder = this.getEditedEntity();

        repairOrder.setOrderNumber(repairOrderService.generateOrderNumber());
        repairOrder.setRepairTime(LocalDateTime.now());
        repairOrder.setOrderStatus(RepairOrderStatus.CREATED);
    }
}