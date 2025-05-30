package com.x.equipment.view.web.checkjob;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.CheckJob;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "check-jobs/:id", layout = MainView.class)
@ViewController(id = "EQUI_CheckJob.detail")
@ViewDescriptor(path = "check-job-detail-view.xml")
@EditedEntityContainer("checkJobDc")
public class CheckJobDetailView extends StandardDetailView<CheckJob> {
    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        CheckJob checkJob = getEditedEntity();
        checkJob.setJobStatus(JobStatus.CREATED);
    }
}