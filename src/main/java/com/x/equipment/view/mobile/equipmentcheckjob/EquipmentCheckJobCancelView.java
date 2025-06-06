package com.x.equipment.view.mobile.equipmentcheckjob;


import com.vaadin.flow.router.Route;
import com.x.equipment.constants.JobStatus;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.CheckJob;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;

import java.time.LocalDateTime;

@Route(value = "equipment-check-job-cancel-view/:id", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentCheckJobCancelView")
@ViewDescriptor(path = "equipment-check-job-cancel-view.xml")
@EditedEntityContainer("checkJobDc")
public class EquipmentCheckJobCancelView  extends StandardDetailView<CheckJob> {

    @Subscribe("saveAction")
    public void onSaveAction(final ActionPerformedEvent event) {
        CheckJob checkJob = this.getEditedEntity();
        checkJob.setJobStatus(JobStatus.CANCELED);
        checkJob.setActualCompleteTime(LocalDateTime.now());
        closeWithSave();
    }
}