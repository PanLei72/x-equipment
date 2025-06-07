package com.x.equipment.view.mobile.equipmentcheckjobquery;


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
import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.CheckJob;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "equipment-check-job-query-view", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentCheckJobQueryView")
@ViewDescriptor(path = "equipment-check-job-query-view.xml")
public class EquipmentCheckJobQueryView extends StandardView {

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
    private CollectionLoader<CheckJob> checkJobsDl;
    @ViewComponent
    private MessageBundle messageBundle;

    @Subscribe
    public void onQueryParametersChange(final QueryParametersChangeEvent event) {
        List<String> categoryParams = event.getQueryParameters().getParameters().get("category");
        if (categoryParams != null && !categoryParams.isEmpty()) {
            checkJobsDl.setParameter("category", categoryParams.getFirst());
        }
    }
    @Supply(to = "virtualList", subject = "renderer")
    private Renderer<CheckJob> virtualListRenderer() {
        return new ComponentRenderer<>(checkJob -> {
            HorizontalLayout rootCardLayout = new HorizontalLayout();
            rootCardLayout.setMargin(true);
            rootCardLayout.addClassNames(LumoUtility.Border.ALL, LumoUtility.Padding.SMALL, LumoUtility.Gap.SMALL, LumoUtility.BorderRadius.SMALL);

            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames(LumoUtility.Padding.Vertical.SMALL, LumoUtility.Gap.SMALL);

            H4 orderNumber = new H4(checkJob.getJobName());
            infoLayout.add(orderNumber);

            HorizontalLayout equipmentLine = createHorizontalLayout();

            H5 equipmentTitle = new H5(messageBundle.getMessage("equipment"));
            equipmentTitle.setClassName("display-white-space");
            Span equipmentNameSpan = new Span(String.valueOf(checkJob.getEquipment().getEquipmentName()));

            equipmentLine.add(equipmentTitle, equipmentNameSpan);


            HorizontalLayout jobStatusLine = createHorizontalLayout();

            H5 jobStatusTitle = new H5(messageBundle.getMessage("jobStatus"));
            jobStatusTitle.setClassName("display-white-space");
            Span jobStatusSpan = this.createJobStatusSpan(checkJob.getJobStatus());

            jobStatusLine.add(jobStatusTitle, jobStatusSpan);

            HorizontalLayout infoLine2 = createHorizontalLayout();
            H5 descriptionTitle = new H5(messageBundle.getMessage("description"));
            descriptionTitle.setClassName("display-white-space");
            Span faultLevelSpan = createCategorySpan(checkJob.getDescription());

            infoLine2.add(descriptionTitle, faultLevelSpan);

            HorizontalLayout infoLine3 = createHorizontalLayout();

            H5 checkCycleTitle = new H5(messageBundle.getMessage("checkCycle"));
            checkCycleTitle.setClassName("display-white-space");
            Span checkCycleSpan = new Span(String.valueOf(checkJob.getCheckCycle()) + checkJob.getCheckCycleUnit());

            infoLine3.add(checkCycleTitle, checkCycleSpan);

            HorizontalLayout infoLine4 = createHorizontalLayout();

            H5 planStartTimeTitle = new H5(messageBundle.getMessage("planStartTime"));
            planStartTimeTitle.setClassName("display-white-space");
            Span planStartTimeSpan = new Span(checkJob.getPlanStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine4.add(planStartTimeTitle, planStartTimeSpan);

            HorizontalLayout infoLine5 = createHorizontalLayout();

            H5 planCompleteTimeTitle = new H5(messageBundle.getMessage("planCompleteTime"));
            planCompleteTimeTitle.setClassName("display-white-space");
            Span planCompleteTimeSpan = new Span(checkJob.getPlanCompleteTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            infoLine5.add(planCompleteTimeTitle, planCompleteTimeSpan);
            infoLayout.add(equipmentLine, jobStatusLine, infoLine2, infoLine3, infoLine4, infoLine5);

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

            Button startButton = new Button(new Icon(VaadinIcon.CHECK_CIRCLE_O));
            startButton.setText(messages.getMessage("actions.Detail"));
            startButton.addClickListener(e ->
                    viewNavigators.detailView(this, CheckJob.class)
                            .withViewClass(EquipmentCheckJobItemQueryView.class)
                            .editEntity(checkJob)
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

    protected Span createCategorySpan(@Nullable String category) {
        Span gradeSpan = new Span(metadataTools.format(category));

        if (category != null) {
            ThemeList categoryThemeList = gradeSpan.getElement().getThemeList();

            switch (category) {
                case "Inspection" -> categoryThemeList.add("badge contrast");
                case "Maintenance" -> categoryThemeList.add("badge primary");
                default -> categoryThemeList.add("badge");
            }
        }

        return gradeSpan;
    }

    protected Span createJobStatusSpan(@Nullable JobStatus jobStatus) {
        Span gradeSpan = new Span(metadataTools.format(jobStatus));

        if (jobStatus != null) {
            ThemeList jobStatusThemeList = gradeSpan.getElement().getThemeList();

            switch (jobStatus) {
                case JobStatus.CREATED -> jobStatusThemeList.add("badge primary");
                case JobStatus.COMPLETED -> jobStatusThemeList.add("badge contrast");
                default -> jobStatusThemeList.add("badge");
            }
        }

        return gradeSpan;
    }
}