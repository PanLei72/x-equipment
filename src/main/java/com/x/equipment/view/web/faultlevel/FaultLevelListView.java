package com.x.equipment.view.web.faultlevel;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import com.x.equipment.entity.FaultLevel;

import com.x.equipment.service.FaultLevelService;
import com.x.equipment.view.web.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "fault-levels", layout = MainView.class)
@ViewController(id = "EQUI_FaultLevel.list")
@ViewDescriptor(path = "fault-level-list-view.xml")
@LookupComponent("faultLevelsDataGrid")
public class FaultLevelListView extends StandardListView<FaultLevel> {

    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @Autowired
    private FaultLevelService faultLevelService;

    @Install(to = "faultLevelsDataGrid.createAction", subject = "initializer")
    private void faultLevelsDataGridCreateActionInitializer(final FaultLevel faultLevel) {
        faultLevel.setFaultLevelCode(faultLevelService.generateName());
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        Element parentElement = getElement().getParent();
        if(parentElement != null && parentElement.getAttribute("class") != null) {
            if (parentElement.getAttribute("class").contains("jmix-dialog")) {
                buttonsPanel.setVisible(false);
            }
        }
    }
}