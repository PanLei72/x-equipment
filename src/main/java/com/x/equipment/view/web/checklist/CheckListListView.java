package com.x.equipment.view.web.checklist;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckList;
import com.x.equipment.service.CheckListService;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "check-lists", layout = MainView.class)
@ViewController(id = "EQUI_CheckList.list")
@ViewDescriptor(path = "check-list-list-view.xml")
@LookupComponent("checkListsDataGrid")
public class CheckListListView extends StandardListView<CheckList> {
    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @Autowired
    private CheckListService checkListService;

    @Install(to = "checkListsDataGrid.createAction", subject = "initializer")
    private void checkListsDataGridCreateActionInitializer(final CheckList checkList) {
        checkList.setActive(true);
        checkList.setCheckListName(checkListService.generateName());
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