package com.x.equipment.view.web.chceklistitem;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.CheckListItem;
import com.x.equipment.view.web.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "chcek-list-items/:id", layout = MainView.class)
@ViewController(id = "EQUI_ChcekListItem.detail")
@ViewDescriptor(path = "chcek-list-item-detail-view.xml")
@EditedEntityContainer("chcekListItemDc")
public class ChcekListItemDetailView extends StandardDetailView<CheckListItem> {
}