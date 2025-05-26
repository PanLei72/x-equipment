package com.x.equipment.view.checklist;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckList;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;


@Route(value = "check-lists", layout = MainView.class)
@ViewController(id = "EQUI_CheckList.list")
@ViewDescriptor(path = "check-list-list-view.xml")
@LookupComponent("checkListsDataGrid")
@DialogMode(width = "64em")
public class CheckListListView extends StandardListView<CheckList> {
    @ViewComponent
    private HorizontalLayout buttonsPanel;

    @Install(to = "checkListsDataGrid.createAction", subject = "initializer")
    private void checkListsDataGridCreateActionInitializer(final CheckList checkList) {
        checkList.setActive(true);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        if(getElement().getParent() != null) {
            if (getElement().getParent().getAttribute("class").contains("jmix-dialog")) {
                buttonsPanel.setVisible(false);
            }
        }
    }
}