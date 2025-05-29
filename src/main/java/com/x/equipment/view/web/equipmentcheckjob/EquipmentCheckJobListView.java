package com.x.equipment.view.web.equipmentcheckjob;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.EquipmentCheckJob;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;


@Route(value = "equipment-check-jobs", layout = MainView.class)
@ViewController(id = "EQUI_EquipmentCheckJob.list")
@ViewDescriptor(path = "equipment-check-job-list-view.xml")
@LookupComponent("equipmentCheckJobsDataGrid")
@DialogMode(width = "64em")
public class EquipmentCheckJobListView extends StandardListView<EquipmentCheckJob> {

}