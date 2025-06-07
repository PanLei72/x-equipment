package com.x.equipment.view.mobile.equipmentcheckjob;


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
import com.x.equipment.constants.CheckItemResult;
import com.x.equipment.constants.CheckListItemType;
import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.CheckJob;
import com.x.equipment.entity.CheckJobItem;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.validation.Validator;
import io.jmix.flowui.data.value.ContainerValueSource;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.model.DataComponents;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Arrays;

@Route(value = "equipment-check-job-item-view/:id", layout = MobileMainView.class)
@ViewController(id = "EQUI_EquipmentCheckJobItemView")
@ViewDescriptor(path = "equipment-check-job-item-view.xml")
@EditedEntityContainer("checkJobDc")
public class EquipmentCheckJobItemView extends StandardDetailView<CheckJob> {


    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    private Messages messages;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private DataComponents dataComponents;
    @ViewComponent
    private InstanceContainer<CheckJob> checkJobDc;
    @ViewComponent
    private MessageBundle messageBundle;

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

            H5 checkMethodTitle = new H5(messageBundle.getMessage("checkMethod"));
            checkMethodTitle.setClassName("display-white-space");
            Span checkMethod = new Span(checkJobItem.getCheckMethod());
            infoLine.add(checkMethodTitle, checkMethod);

            HorizontalLayout infoLine1 = createHorizontalLayout();
            infoLine1.addClassName(LumoUtility.AlignItems.CENTER);

            H5 categoryTitle = new H5(messageBundle.getMessage("category"));
            categoryTitle.setClassName("display-white-space");
            Span category = this.createGradeSpan(checkJobItem.getCategory());
            infoLine1.add(categoryTitle, category);

            HorizontalLayout infoLine2 = createHorizontalLayout();
            H5 descriptionTitle = new H5(messageBundle.getMessage("description"));
            descriptionTitle.setClassName("display-white-space");
            Span faultLevelSpan = new Span(checkJobItem.getDescription());

            infoLine2.add(descriptionTitle, faultLevelSpan);


            HorizontalLayout infoLine4 = createHorizontalLayout();

            H5 standardValueTitle = new H5(messageBundle.getMessage("standardValue"));
            standardValueTitle.setClassName("display-white-space");
            Span standardValueSpan = new Span(checkJobItem.getStandardValue() != null ? metadataTools.format(checkJobItem.getStandardValue()) : "");
            infoLine4.add(standardValueTitle, standardValueSpan);


            HorizontalLayout infoLine5 = createHorizontalLayout();

            H5 lowerLimitValueTitle = new H5(messageBundle.getMessage("lowerLimitValue"));
            lowerLimitValueTitle.setClassName("display-white-space");
            Span lowerLimitValueSpan = new Span(checkJobItem.getLowerLimitValue() != null ? metadataTools.format(checkJobItem.getLowerLimitValue()) : "");
            infoLine5.add(lowerLimitValueTitle, lowerLimitValueSpan);


            HorizontalLayout infoLine6 = createHorizontalLayout();

            H5 upperLimitValueTitle = new H5(messageBundle.getMessage("upperLimitValue"));
            upperLimitValueTitle.setClassName("display-white-space");
            Span UpperLimitValueSpan = new Span(checkJobItem.getUpperLimitValue() != null ? metadataTools.format(checkJobItem.getUpperLimitValue()) : "");
            infoLine6.add(upperLimitValueTitle, UpperLimitValueSpan);


            HorizontalLayout infoLine7 = createHorizontalLayout();

            H5 checkResultTitle = new H5(messageBundle.getMessage("checkResult"));
            checkResultTitle.setClassName("display-white-space");

            infoLine7.add(checkResultTitle);

            if(CheckListItemType.BOOLEAN.equals(checkJobItem.getCheckListItemType())) {
                JmixRadioButtonGroup checkResultField = uiComponents.create(JmixRadioButtonGroup.class);
                checkResultField.setItems(Arrays.stream(CheckItemResult.values()).map(Enum::name).toList());
                checkResultField.setValueSource(new ContainerValueSource<>(checkJobItemDc, "checkResult"));
                infoLine7.add(checkResultField);
            }else if(CheckListItemType.NUMBER.equals(checkJobItem.getCheckListItemType())) {
                TypedTextField<String> checkResultField = uiComponents.create(TypedTextField.class);
                checkResultField.setClearButtonVisible(true);
                checkResultField.addValidator(new Validator<String>() {
                    @Override
                    public void accept(String value) {
                        if (StringUtils.isBlank(value)) {
                            return;
                        }
                        // 使用正则表达式校验小数或整数（支持正负号和小数点）
                        boolean validation = value.matches("^-?\\d*\\.?\\d+$");
                        if(!validation)
                        {
                            throw new ValidationException(messageBundle.getMessage("checkResultNotNumber"));
                        }

                    }
                });
                checkResultField.setValueSource(new ContainerValueSource<>(checkJobItemDc, "checkResult"));
                infoLine7.add(checkResultField);
            }else if(CheckListItemType.LIST.equals(checkJobItem.getCheckListItemType())) {
                JmixComboBox<String> checkResultComboBox = uiComponents.create(JmixComboBox.class);
                checkResultComboBox.setValueSource(new ContainerValueSource<>(checkJobItemDc, "checkResult"));
                infoLine7.add(checkResultComboBox);
            }else {
                TypedTextField<String> checkResultField = uiComponents.create(TypedTextField.class);
                checkResultField.setClearButtonVisible(true);
                checkResultField.setValueSource(new ContainerValueSource<>(checkJobItemDc, "checkResult"));
                infoLine7.add(checkResultField);
            }

            HorizontalLayout infoLine8 = createHorizontalLayout();
            H5 remarkTitle = new H5(messageBundle.getMessage("remark"));
            remarkTitle.setClassName("display-white-space");
            TypedTextField<String> remarkField = uiComponents.create(TypedTextField.class);
            remarkField.setClearButtonVisible(true);
            remarkField.setValueSource(new ContainerValueSource<>(checkJobItemDc, "remark"));

            infoLine8.add(remarkTitle, remarkField);


            infoLayout.add(infoLine, infoLine1, infoLine2, infoLine4, infoLine5,infoLine6, infoLine7, infoLine8);

            rootCardLayout.add(infoLayout);

            return rootCardLayout;
        });
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
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