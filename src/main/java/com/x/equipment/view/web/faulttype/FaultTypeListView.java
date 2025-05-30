package com.x.equipment.view.web.faulttype;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.x.equipment.entity.FaultType;
import com.x.equipment.service.FaultTypeService;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "fault-types", layout = MainView.class)
@ViewController(id = "EQUI_FaultType.list")
@ViewDescriptor(path = "fault-type-list-view.xml")
@LookupComponent("faultTypesDataGrid")
public class FaultTypeListView extends StandardListView<FaultType> {

    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @Autowired
    private FaultTypeService faultTypeService;

    @Install(to = "faultTypesDataGrid.createAction", subject = "initializer")
    private void faultTypesDataGridCreateActionInitializer(final FaultType faultType) {
        faultType.setFaultTypeCode(faultTypeService.generateName());
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