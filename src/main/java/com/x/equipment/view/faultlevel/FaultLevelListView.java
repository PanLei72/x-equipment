package com.x.equipment.view.faultlevel;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import com.x.equipment.entity.FaultLevel;

import com.x.equipment.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "fault-levels", layout = MainView.class)
@ViewController(id = "EQUI_FaultLevel.list")
@ViewDescriptor(path = "fault-level-list-view.xml")
@LookupComponent("faultLevelsDataGrid")
@DialogMode(width = "64em")
public class FaultLevelListView extends StandardListView<FaultLevel> {

    @ViewComponent
    private HorizontalLayout buttonsPanel;

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