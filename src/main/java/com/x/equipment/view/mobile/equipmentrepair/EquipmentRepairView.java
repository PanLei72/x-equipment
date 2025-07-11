package com.x.equipment.view.mobile.equipmentrepair;


import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
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
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.format.DateTimeFormatter;

@Route(value = "equipment-repair-view", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentRepairView")
@ViewDescriptor(path = "equipment-repair-view.xml")
public class EquipmentRepairView extends StandardView {
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

            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames(LumoUtility.Padding.Vertical.SMALL, LumoUtility.Gap.SMALL);

            H4 orderNumber = new H4(repairOrder.getOrderNumber());


            Span equipmentNameSpan = new Span(String.valueOf(repairOrder.getEquipment().getEquipmentName()));

            infoLayout.add(orderNumber, equipmentNameSpan);

            HorizontalLayout infoLine = createHorizontalLayout();
            infoLine.addClassName(LumoUtility.AlignItems.CENTER);

            H5 faultTypeTitle = new H5(messageBundle.getMessage("faultType"));
            faultTypeTitle.setClassName("display-white-space");
            Span faultType = new Span(repairOrder.getFaultType().getFaultTypeCode() + "（" + repairOrder.getFaultType().getDescription() + "）");
            infoLine.add(faultTypeTitle, faultType);

            HorizontalLayout infoLine2 = createHorizontalLayout();
            H5 faultLevelTitle = new H5(messageBundle.getMessage("faultLevel"));
            faultLevelTitle.setClassName("display-white-space");
            Span faultLevelSpan = createGradeSpan(repairOrder.getFaultLevel().getFaultLevelCode() + "（" + repairOrder.getFaultLevel().getDescription() + "）");

            infoLine2.add(faultLevelTitle, faultLevelSpan);

            HorizontalLayout infoLine3 = createHorizontalLayout();

            H5 descriptionTitle = new H5(messageBundle.getMessage("description"));
            descriptionTitle.setClassName("display-white-space");
            Span descriptionSpan = new Span(String.valueOf(repairOrder.getDescription()));

            infoLine3.add(descriptionTitle, descriptionSpan);

            HorizontalLayout infoLine4 = createHorizontalLayout();

            H5 repairTimeTitle = new H5(messageBundle.getMessage("repairTime"));
            repairTimeTitle.setClassName("display-white-space");
            Span repairTimeSpan = new Span(repairOrder.getRepairTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine4.add(repairTimeTitle, repairTimeSpan);


            VerticalLayout detailInfoLayout = createVerticalLayout();
            detailInfoLayout.add(infoLine, infoLine2, infoLine3, infoLine4);

            JmixDetails infoDetails = uiComponents.create(JmixDetails.class);
            infoDetails.setSummaryText(messageBundle.getMessage("infoDetails"));
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

            if(RepairOrderStatus.CREATED.equals(repairOrder.getOrderStatus())) {
                Button startButton = new Button(new Icon(VaadinIcon.CHEVRON_CIRCLE_RIGHT_O));
                startButton.setText(messages.getMessage("actions.Start"));
                startButton.addClickListener(e ->
                        viewNavigators.detailView(this, RepairOrder.class)
                                .withViewClass(RepairOrderStartView.class)
                                .editEntity(repairOrder)
                                .withBackwardNavigation(true)
                                .navigate());
//            startButton.addClickListener(e -> dialogWindows.detail(this, RepairOrder.class)
//                    .withViewClass(RepairOrderStartView.class)
//                    .editEntity(repairOrder)
//                    .withAfterCloseListener(closeEvent -> {
//                        if (closeEvent.closedWith(StandardOutcome.SAVE)) {
//                            repairOrdersDc.replaceItem(closeEvent.getSource().getView().getEditedEntity());
//                        }
//                    })
//                    .open());
                startButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

                buttonsPanel.add(startButton);
            }

            if(RepairOrderStatus.IN_PROGRESS.equals(repairOrder.getOrderStatus())) {
                Button completeButton = new Button(new Icon(VaadinIcon.CHECK_CIRCLE_O));
                completeButton.setText(messages.getMessage("actions.Complete"));
                completeButton.addClickListener(e ->
                        viewNavigators.detailView(this, RepairOrder.class)
                                .withViewClass(RepairOrderCompleteView.class)
                                .editEntity(repairOrder)
                                .withBackwardNavigation(true)
                                .navigate());
                completeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

                buttonsPanel.add(completeButton);
            }


            Button cancelButton = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE_O));
            cancelButton.setText(messages.getMessage("actions.Cancel"));
            cancelButton.addClickListener(e ->
                    viewNavigators.detailView(this, RepairOrder.class)
                            .withViewClass(RepairOrderCancelView.class)
                            .editEntity(repairOrder)
                            .withBackwardNavigation(true)
                            .navigate());
            cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

            buttonsPanel.add(cancelButton);

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