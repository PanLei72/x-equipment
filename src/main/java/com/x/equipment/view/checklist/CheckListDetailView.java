package com.x.equipment.view.checklist;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckList;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "check-lists/:id", layout = MainView.class)
@ViewController(id = "EQUI_CheckList.detail")
@ViewDescriptor(path = "check-list-detail-view.xml")
@EditedEntityContainer("checkListDc")
@DialogMode(width = "64em")
public class CheckListDetailView extends StandardDetailView<CheckList> {
}