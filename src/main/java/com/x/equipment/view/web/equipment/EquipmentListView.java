package com.x.equipment.view.web.equipment;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.x.equipment.entity.Equipment;
import com.x.equipment.service.EquipmentService;
import com.x.equipment.view.web.main.MainView;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "equipments", layout = MainView.class)
@ViewController("EQUI_Equipment.list")
@ViewDescriptor("equipment-list-view.xml")
@LookupComponent("equipmentsDataGrid")
@DialogMode(width = "64em")
public class EquipmentListView extends StandardListView<Equipment> {
    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private EquipmentService equipmentService;

    @Install(to = "equipmentsDataGrid.create", subject = "initializer")
    private void equipmentsDataGridCreateInitializer(final Equipment equipment) {
        equipment.setEquipmentName(equipmentService.generateName());
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

    @Supply(to = "equipmentsDataGrid.image", subject = "renderer")
    private Renderer<Equipment> equipmentsDataGridImageRenderer() {
        return new ComponentRenderer<>(equipment -> {
            FileRef fileRef = equipment.getImage();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource);
                image.setClassName("user-picture");

                return image;
            } else {
                return null;
            }
        });
    }

}