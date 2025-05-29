package com.x.equipment.view.equipmentcheckjob;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.EquipmentCheckJob;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "equipment-check-jobs/:id", layout = MainView.class)
@ViewController(id = "EQUI_EquipmentCheckJob.detail")
@ViewDescriptor(path = "equipment-check-job-detail-view.xml")
@EditedEntityContainer("equipmentCheckJobDc")
public class EquipmentCheckJobDetailView extends StandardDetailView<EquipmentCheckJob> {
    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        EquipmentCheckJob equipmentCheckJob = getEditedEntity();
        equipmentCheckJob.setJobStatus(JobStatus.NEW);
    }
}