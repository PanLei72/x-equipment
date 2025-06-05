package com.x.equipment.view.mobile.equipmentrepair;


import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
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
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.mobile.main.MobileMainView;
import com.x.equipment.view.web.main.MainView;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.format.DateTimeFormatter;

@Route(value = "equipment-repair-confirm-view", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentRepairConfirmView")
@ViewDescriptor(path = "equipment-repair-confirm-view.xml")
public class EquipmentRepairConfirmView extends StandardView {

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

    @Supply(to = "virtualList", subject = "renderer")
    private Renderer<RepairOrder> virtualListRenderer() {
        return new ComponentRenderer<>(repairOrder -> {
            HorizontalLayout rootCardLayout = new HorizontalLayout();
            rootCardLayout.setMargin(true);

            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames(LumoUtility.Padding.Vertical.SMALL, LumoUtility.Gap.SMALL);

            H4 orderNumber = new H4(repairOrder.getOrderNumber());


            Span equipmentNameSpan = new Span(String.valueOf(repairOrder.getEquipment().getEquipmentName()));

            infoLayout.add(orderNumber, equipmentNameSpan);

            HorizontalLayout infoLine = createHorizontalLayout();
            infoLine.addClassName(LumoUtility.AlignItems.CENTER);

            H5 faultTypeTitle = new H5("故障类型:");
            faultTypeTitle.setClassName("display-white-space");
            Span faultType = new Span(repairOrder.getFaultType().getFaultTypeCode() + "（" + repairOrder.getFaultType().getDescription() + "）");
            infoLine.add(faultTypeTitle, faultType);

            HorizontalLayout infoLine2 = createHorizontalLayout();
            H5 faultLevelTitle = new H5("故障等级:");
            faultLevelTitle.setClassName("display-white-space");
            Span faultLevelSpan = createGradeSpan(repairOrder.getFaultLevel().getFaultLevelCode() + "（" + repairOrder.getFaultLevel().getDescription() + "）");

            infoLine2.add(faultLevelTitle, faultLevelSpan);

            HorizontalLayout infoLine3 = createHorizontalLayout();

            H5 descriptionTitle = new H5("描述:");
            descriptionTitle.setClassName("display-white-space");
            Span descriptionSpan = new Span(String.valueOf(repairOrder.getDescription()));

            infoLine3.add(descriptionTitle, descriptionSpan);

            HorizontalLayout infoLine4 = createHorizontalLayout();

            H5 repairTimeTitle = new H5("报修时间:");
            repairTimeTitle.setClassName("display-white-space");
            Span repairTimeSpan = new Span(repairOrder.getRepairTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine4.add(repairTimeTitle, repairTimeSpan);

            HorizontalLayout infoLine5 = createHorizontalLayout();

            H5 startRepairTimeTitle = new H5("开始时间:");
            startRepairTimeTitle.setClassName("display-white-space");
            Span startRepairTimeTitleSpan = new Span(repairOrder.getStartRepairTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine5.add(startRepairTimeTitle, startRepairTimeTitleSpan);


            HorizontalLayout infoLine6 = createHorizontalLayout();

            H5 completeRepairTimeTitle = new H5("完成时间:");
            completeRepairTimeTitle.setClassName("display-white-space");
            Span completeRepairTimeTitleSpan = new Span(repairOrder.getCompleteRepairTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine6.add(completeRepairTimeTitle, completeRepairTimeTitleSpan);


            VerticalLayout detailInfoLayout = createVerticalLayout();
            detailInfoLayout.add(infoLine, infoLine2, infoLine3, infoLine4, infoLine5, infoLine6);

            JmixDetails infoDetails = uiComponents.create(JmixDetails.class);
            infoDetails.setSummaryText("详细信息");
            infoDetails.setContent(detailInfoLayout);

            infoLayout.add(infoDetails, new Hr());


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



            VerticalLayout buttonsPanel = new VerticalLayout();
            buttonsPanel.setWidth("AUTO");
            buttonsPanel.setPadding(false);
            buttonsPanel.setSpacing(false);

            Button startButton = new Button(new Icon(VaadinIcon.CHECK_CIRCLE_O));
            startButton.setText(messages.getMessage("actions.Confirm"));
            startButton.addClickListener(e ->
                    viewNavigators.detailView(this, RepairOrder.class)
                            .withViewClass(RepairOrderCloseView.class)
                            .editEntity(repairOrder)
                            .withBackwardNavigation(true)
                            .navigate());
            startButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);



            buttonsPanel.add(startButton);

            infoLayout.add(avatar, buttonsPanel);


            rootCardLayout.add(avatar, infoLayout, buttonsPanel);


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

    protected Span createGradeSpan(@Nullable String grade) {
        Span gradeSpan = new Span(metadataTools.format(grade));

        if (grade != null) {
            ThemeList gradeThemeList = gradeSpan.getElement().getThemeList();

            switch (grade) {
                case "A" -> gradeThemeList.add("badge contrast");
                case "B" -> gradeThemeList.add("badge primary");
                default -> gradeThemeList.add("badge");
            }
        }

        return gradeSpan;
    }

}