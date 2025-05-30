package com.x.equipment.view.web.checkplan;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckPlan;
import com.x.equipment.service.CheckPlanService;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "check-plans", layout = MainView.class)
@ViewController(id = "EQUI_CheckPlan.list")
@ViewDescriptor(path = "check-plan-list-view.xml")
@LookupComponent("checkPlansDataGrid")
@DialogMode(width = "64em")
public class CheckPlanListView extends StandardListView<CheckPlan> {
    @Autowired
    private CheckPlanService checkPlanService;

    @Install(to = "checkPlansDataGrid.createAction", subject = "initializer")
    private void checkPlansDataGridCreateActionInitializer(final CheckPlan checkPlan) {
        checkPlan.setCheckPlanName(checkPlanService.generateName());
    }
}