package com.x.equipment.view.checkplan;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckPlan;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;


@Route(value = "check-plans", layout = MainView.class)
@ViewController(id = "EQUI_CheckPlan.list")
@ViewDescriptor(path = "check-plan-list-view.xml")
@LookupComponent("checkPlansDataGrid")
@DialogMode(width = "64em")
public class CheckPlanListView extends StandardListView<CheckPlan> {
}