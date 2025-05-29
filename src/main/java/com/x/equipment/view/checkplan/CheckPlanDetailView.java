package com.x.equipment.view.checkplan;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckPlan;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "check-plans/:id", layout = MainView.class)
@ViewController(id = "EQUI_CheckPlan.detail")
@ViewDescriptor(path = "check-plan-detail-view.xml")
@EditedEntityContainer("checkPlanDc")
public class CheckPlanDetailView extends StandardDetailView<CheckPlan> {
}