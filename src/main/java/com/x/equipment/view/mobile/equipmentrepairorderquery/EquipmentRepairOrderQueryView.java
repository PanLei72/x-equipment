package com.x.equipment.view.mobile.equipmentrepairorderquery;


import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.x.equipment.constants.RepairOrderStatus;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.mobile.equipmentcheckjobquery.EquipmentCheckJobItemQueryView;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.format.DateTimeFormatter;

@Route(value = "equipment-repair-order-query-view", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentRepairOrderQueryView")
@ViewDescriptor(path = "equipment-repair-order-query-view.xml")
public class EquipmentRepairOrderQueryView extends StandardView {


    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    private Messages messages;
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private ViewNavigators viewNavigators;
    @ViewComponent
    private MessageBundle messageBundle;

    @Supply(to = "virtualList", subject = "renderer")
    private Renderer<RepairOrder> virtualListRenderer() {
        return new ComponentRenderer<>(repairOrder -> {
            HorizontalLayout rootCardLayout = new HorizontalLayout();
            rootCardLayout.setMargin(true);
            rootCardLayout.addClassNames(LumoUtility.Border.ALL, LumoUtility.Padding.SMALL, LumoUtility.Gap.SMALL, LumoUtility.BorderRadius.SMALL);

            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames(LumoUtility.Padding.Vertical.SMALL, LumoUtility.Gap.SMALL);

            H4 orderNumber = new H4(repairOrder.getOrderNumber());

            infoLayout.add(orderNumber);

            HorizontalLayout infoLine = createHorizontalLayout();
            infoLine.addClassName(LumoUtility.AlignItems.CENTER);

            H5 equipmentTitle = new H5(messageBundle.getMessage("equipment"));
            equipmentTitle.setClassName("display-white-space");
            Span equipmentName = new Span(repairOrder.getEquipment().getEquipmentName());
            infoLine.add(equipmentTitle, equipmentName);

            HorizontalLayout infoLine2 = createHorizontalLayout();
            H5 descriptionTitle = new H5(messageBundle.getMessage("description"));
            descriptionTitle.setClassName("display-white-space");
            Span faultLevelSpan = new Span(repairOrder.getDescription());

            infoLine2.add(descriptionTitle, faultLevelSpan);

            HorizontalLayout infoLine3 = createHorizontalLayout();

            H5 faultTypeTitle = new H5(messageBundle.getMessage("faultType"));
            faultTypeTitle.setClassName("display-white-space");
            Span faultTypeCodeSpan = new Span(repairOrder.getFaultType().getFaultTypeCode());

            infoLine3.add(faultTypeTitle, faultTypeCodeSpan);

            HorizontalLayout infoLine31= createHorizontalLayout();

            H5 orderStatusTitle = new H5(messageBundle.getMessage("orderStatus"));
            orderStatusTitle.setClassName("display-white-space");
            Span orderStatusTitleSpan = this.createOrderStatusSpan(repairOrder.getOrderStatus());

            infoLine31.add(orderStatusTitle, orderStatusTitleSpan);

            HorizontalLayout infoLine4 = createHorizontalLayout();

            H5 repairTimeTitle = new H5(messageBundle.getMessage("repairTime"));
            repairTimeTitle.setClassName("display-white-space");
            Span repairTimeSpan = new Span(repairOrder.getRepairTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine4.add(repairTimeTitle, repairTimeSpan);


            HorizontalLayout infoLine5 = createHorizontalLayout();

            H5 startTimeTitle = new H5(messageBundle.getMessage("startTime"));
            startTimeTitle.setClassName("display-white-space");
            Span startTimeSpan = new Span(repairOrder.getStartRepairTime() != null ?
                    repairOrder.getStartRepairTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);

            infoLine5.add(startTimeTitle, startTimeSpan);


            HorizontalLayout infoLine6 = createHorizontalLayout();


            H5 completeTimeTitle = new H5(messageBundle.getMessage("completeTime"));
            completeTimeTitle.setClassName("display-white-space");
            Span completeTimeSpan = new Span(repairOrder.getCompleteRepairTime() != null ?
                    repairOrder.getCompleteRepairTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")): null);

            infoLine6.add(completeTimeTitle, completeTimeSpan);


            infoLayout.add(infoLine, infoLine2, infoLine3, infoLine31, infoLine4, infoLine5, infoLine6);

            Avatar avatar = new Avatar();
            avatar.addThemeVariants(AvatarVariant.LUMO_XLARGE);
            avatar.setImage("/equipment/icons/no-image.svg");

            if (repairOrder.getEquipment() != null && repairOrder.getEquipment().getImage() != null) {
                FileRef fileRef = repairOrder.getEquipment().getImage();
                if (fileRef != null) {
                    StreamResource streamResource = new StreamResource(
                            fileRef.getFileName(),
                            () -> fileStorage.openStream(fileRef));

                    avatar.setImageResource(streamResource);
                }
            }


            rootCardLayout.add(avatar, infoLayout);


            return rootCardLayout;
        });
    }


    protected VerticalLayout createVerticalLayout() {
        VerticalLayout layout = uiComponents.create(VerticalLayout.class);
        layout.setSpacing(false);
        layout.setPadding(false);
        return layout;
    }

    protected HorizontalLayout createHorizontalLayout() {
        HorizontalLayout layout = uiComponents.create(HorizontalLayout.class);
        layout.setPadding(false);
        return layout;
    }

    protected Span createOrderStatusSpan(@Nullable RepairOrderStatus repairOrderStatus) {
        Span gradeSpan = new Span(metadataTools.format(repairOrderStatus));

        if (repairOrderStatus != null) {
            ThemeList gradeThemeList = gradeSpan.getElement().getThemeList();

            switch (repairOrderStatus) {
                case RepairOrderStatus.CREATED -> gradeThemeList.add("badge contrast");
                case RepairOrderStatus.IN_PROGRESS -> gradeThemeList.add("badge primary");
                case RepairOrderStatus.COMPLETED -> gradeThemeList.add("badge primary");
                case RepairOrderStatus.CANCELED -> gradeThemeList.add("badge primary");
                case RepairOrderStatus.CLOSED -> gradeThemeList.add("badge primary");
                default -> gradeThemeList.add("badge");
            }
        }

        return gradeSpan;
    }

}