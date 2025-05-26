package com.x.equipment.view.equipmentclass;

import com.vaadin.flow.router.Route;
import com.x.equipment.entity.EquipmentClass;
import com.x.equipment.entity.EquipmentClassEquipment;
import com.x.equipment.view.equipmentclassequipment.EquipmentClassEquipmentListView;
import com.x.equipment.view.main.MainView;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "equipmentClasses/:id", layout = MainView.class)
@ViewController("EQUI_EquipmentClass.detail")
@ViewDescriptor("equipment-class-detail-view.xml")
@EditedEntityContainer("equipmentClassDc")
@DialogMode(width = "64em")
public class EquipmentClassDetailView extends StandardDetailView<EquipmentClass> {
    @Autowired
    private DialogWindows dialogWindows;
    @ViewComponent
    private DataGrid<EquipmentClassEquipment> equipmentClassEquipmentsDataGrid;

    @Subscribe("equipmentClassEquipmentsDataGrid.add")
    public void onEquipmentClassEquipmentsDataGridAdd(final ActionPerformedEvent event) {
        DialogWindow<EquipmentClassEquipmentListView> lookupDialog = dialogWindows.lookup(equipmentClassEquipmentsDataGrid)
                .withViewClass(EquipmentClassEquipmentListView.class)
                .build();

        lookupDialog.getView().setEquipmentClass(this.getEditedEntity());

        lookupDialog.open();
    }

}