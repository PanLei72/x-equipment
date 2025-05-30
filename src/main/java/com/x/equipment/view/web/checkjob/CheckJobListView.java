package com.x.equipment.view.web.checkjob;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckJob;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;


@Route(value = "check-jobs", layout = MainView.class)
@ViewController(id = "EQUI_CheckJob.list")
@ViewDescriptor(path = "check-job-list-view.xml")
@LookupComponent("checkJobsDataGrid")
public class CheckJobListView extends StandardListView<CheckJob> {

}