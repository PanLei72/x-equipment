package com.x.equipment.view.mobile.equipmentcheckjobquery;


import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.CheckJob;
import com.x.equipment.entity.CheckJobItem;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.DataComponents;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Route(value = "equipment-check-job-item-query-view/:id", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentCheckJobItemQueryView")
@ViewDescriptor(path = "equipment-check-job-item-query-view.xml")
@EditedEntityContainer("checkJobDc")
public class EquipmentCheckJobItemQueryView extends StandardDetailView<CheckJob>  {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    private DataComponents dataComponents;
    @ViewComponent
    private InstanceContainer<CheckJob> checkJobDc;

    @Supply(to = "virtualList", subject = "renderer")
    private Renderer<CheckJobItem> virtualListRenderer() {
        return new ComponentRenderer<>(checkJobItem -> {
            InstanceContainer<CheckJobItem> checkJobItemDc = dataComponents.createInstanceContainer(CheckJobItem.class);
            checkJobItemDc.setItem(checkJobItem);

            HorizontalLayout rootCardLayout = new HorizontalLayout();
            rootCardLayout.setMargin(true);
            rootCardLayout.addClassNames(LumoUtility.Border.ALL, LumoUtility.Padding.SMALL, LumoUtility.Gap.SMALL, LumoUtility.BorderRadius.SMALL);

            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames(LumoUtility.Padding.Vertical.SMALL, LumoUtility.Gap.SMALL);

            H4 orderNumber = new H4(checkJobItem.getJobItemName());
            infoLayout.add(orderNumber);

            HorizontalLayout infoLine = createHorizontalLayout();
            infoLine.addClassName(LumoUtility.AlignItems.CENTER);

            H5 checkMethodTitle = new H5("检查方法:");
            checkMethodTitle.setClassName("display-white-space");
            Span checkMethod = new Span(checkJobItem.getCheckMethod());
            infoLine.add(checkMethodTitle, checkMethod);

            HorizontalLayout infoLine1 = createHorizontalLayout();
            infoLine1.addClassName(LumoUtility.AlignItems.CENTER);

            H5 categoryTitle = new H5("分类:");
            categoryTitle.setClassName("display-white-space");
            Span category = new Span(checkJobItem.getCategory());
            infoLine1.add(categoryTitle, category);

            HorizontalLayout infoLine2 = createHorizontalLayout();
            H5 descriptionTitle = new H5("描述:");
            descriptionTitle.setClassName("display-white-space");
            Span faultLevelSpan = createGradeSpan(checkJobItem.getDescription());

            infoLine2.add(descriptionTitle, faultLevelSpan);

            HorizontalLayout infoLine3 = createHorizontalLayout();

            H5 checkCycleTitle = new H5("检查项类型:");
            checkCycleTitle.setClassName("display-white-space");
            Span checkCycleSpan = new Span(String.valueOf(checkJobItem.getCheckListItemType()));

            infoLine3.add(checkCycleTitle, checkCycleSpan);

            HorizontalLayout infoLine4 = createHorizontalLayout();

            H5 standardValueTitle = new H5("标准值:");
            standardValueTitle.setClassName("display-white-space");
            Span standardValueSpan = new Span(checkJobItem.getStandardValue() != null ? metadataTools.format(checkJobItem.getStandardValue()) : "");
            infoLine4.add(standardValueTitle, standardValueSpan);


            HorizontalLayout infoLine5 = createHorizontalLayout();

            H5 lowerLimitValueTitle = new H5("下限值:");
            lowerLimitValueTitle.setClassName("display-white-space");
            Span lowerLimitValueSpan = new Span(checkJobItem.getLowerLimitValue() != null ? metadataTools.format(checkJobItem.getLowerLimitValue()) : "");
            infoLine5.add(lowerLimitValueTitle, lowerLimitValueSpan);


            HorizontalLayout infoLine6 = createHorizontalLayout();

            H5 upperLimitValueTitle = new H5("上限值:");
            upperLimitValueTitle.setClassName("display-white-space");
            Span UpperLimitValueSpan = new Span(checkJobItem.getUpperLimitValue() != null ? metadataTools.format(checkJobItem.getUpperLimitValue()) : "");
            infoLine6.add(upperLimitValueTitle, UpperLimitValueSpan);


            HorizontalLayout infoLine7 = createHorizontalLayout();

            H5 checkResultTitle = new H5("检查结果:");
            checkResultTitle.setClassName("display-white-space");
            Span checkResultSpan = new Span(checkJobItem.getCheckResult());
            infoLine7.add(upperLimitValueTitle, checkResultSpan);


            infoLayout.add(infoLine, infoLine1, infoLine2, infoLine3, infoLine4, infoLine5,infoLine6, infoLine7);

            rootCardLayout.add(infoLayout);

            return rootCardLayout;
        });
    }

    @Subscribe
    public void onBeforeSave(final StandardDetailView.BeforeSaveEvent event) {
        CheckJob checkJob = this.getEditedEntity();
        checkJob.setJobStatus(JobStatus.COMPLETED);
        checkJob.setActualCompleteTime(LocalDateTime.now());
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