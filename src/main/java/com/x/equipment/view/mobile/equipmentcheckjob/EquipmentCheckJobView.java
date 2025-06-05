package com.x.equipment.view.mobile.equipmentcheckjob;


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
import com.x.equipment.entity.CheckJob;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.format.DateTimeFormatter;

@Route(value = "equipment-check-job-view", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentCheckJobView")
@ViewDescriptor(path = "equipment-check-job-view.xml")
public class EquipmentCheckJobView extends StandardView {

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
    private Renderer<CheckJob> virtualListRenderer() {
        return new ComponentRenderer<>(checkJob -> {
            HorizontalLayout rootCardLayout = new HorizontalLayout();
            rootCardLayout.setMargin(true);
            rootCardLayout.addClassNames(LumoUtility.Border.ALL, LumoUtility.Padding.SMALL, LumoUtility.Gap.SMALL, LumoUtility.BorderRadius.SMALL);

            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames(LumoUtility.Padding.Vertical.SMALL, LumoUtility.Gap.SMALL);

            H4 orderNumber = new H4(checkJob.getJobName());


            Span equipmentNameSpan = new Span(String.valueOf(checkJob.getEquipment().getEquipmentName()));

            infoLayout.add(orderNumber, equipmentNameSpan);

            HorizontalLayout infoLine = createHorizontalLayout();
            infoLine.addClassName(LumoUtility.AlignItems.CENTER);

            H5 categoryTitle = new H5("分类:");
            categoryTitle.setClassName("display-white-space");
            Span category = new Span(checkJob.getCategory());
            infoLine.add(categoryTitle, category);

            HorizontalLayout infoLine2 = createHorizontalLayout();
            H5 descriptionTitle = new H5("描述:");
            descriptionTitle.setClassName("display-white-space");
            Span faultLevelSpan = createGradeSpan(checkJob.getDescription());

            infoLine2.add(descriptionTitle, faultLevelSpan);

            HorizontalLayout infoLine3 = createHorizontalLayout();

            H5 checkCycleTitle = new H5("检查周期:");
            checkCycleTitle.setClassName("display-white-space");
            Span checkCycleSpan = new Span(String.valueOf(checkJob.getCheckCycle()) + checkJob.getCheckCycleUnit());

            infoLine3.add(checkCycleTitle, checkCycleSpan);

            HorizontalLayout infoLine4 = createHorizontalLayout();

            H5 planStartTimeTitle = new H5("计划开始时间:");
            planStartTimeTitle.setClassName("display-white-space");
            Span planStartTimeSpan = new Span(checkJob.getPlanStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine4.add(planStartTimeTitle, planStartTimeSpan);

            HorizontalLayout infoLine5 = createHorizontalLayout();

            H5 planCompleteTimeTitle = new H5("计划完成时间:");
            planCompleteTimeTitle.setClassName("display-white-space");
            Span planCompleteTimeSpan = new Span(checkJob.getPlanCompleteTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine5.add(planCompleteTimeTitle, planCompleteTimeSpan);


            infoLayout.add(infoLine, infoLine2, infoLine3, infoLine4, infoLine5);

            Avatar avatar = new Avatar();
            avatar.addThemeVariants(AvatarVariant.LUMO_XLARGE);
            avatar.setImage("/equipment/icons/no-image.svg");

            if (checkJob.getEquipment() != null && checkJob.getEquipment().getImage() != null) {
                FileRef fileRef = checkJob.getEquipment().getImage();
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

            Button completeButton = new Button(new Icon(VaadinIcon.CHECK_CIRCLE_O));
            completeButton.setText(messages.getMessage("actions.Complete"));
            completeButton.addClickListener(e ->
                    viewNavigators.detailView(this, CheckJob.class)
                            .withViewClass(EquipmentCheckJobItemView.class)
                            .editEntity(checkJob)
                            .withBackwardNavigation(true)
                            .navigate());
            completeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

            buttonsPanel.add(completeButton);

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