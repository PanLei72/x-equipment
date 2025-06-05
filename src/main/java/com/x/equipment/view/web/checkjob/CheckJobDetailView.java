package com.x.equipment.view.web.checkjob;

import com.vaadin.flow.router.Route;
import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.CheckJob;
import com.x.equipment.entity.CheckJobItem;
import com.x.equipment.entity.CheckList;
import com.x.equipment.entity.CheckListItem;
import com.x.equipment.service.CheckJobService;
import com.x.equipment.view.web.main.MainView;
import io.jmix.core.DataManager;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "check-jobs/:id", layout = MainView.class)
@ViewController(id = "EQUI_CheckJob.detail")
@ViewDescriptor(path = "check-job-detail-view.xml")
@EditedEntityContainer("checkJobDc")
public class CheckJobDetailView extends StandardDetailView<CheckJob> {
    @Autowired
    private CheckJobService checkJobService;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        CheckJob checkJob = getEditedEntity();
        checkJob.setJobName(checkJobService.generateJobName());
        checkJob.setJobStatus(JobStatus.CREATED);

        CheckList checkList = checkJob.getChecklist();
        List<CheckListItem> checkListItemList = checkList.getCheckListItems();
        if(checkListItemList != null && !checkListItemList.isEmpty())
        {
            List<CheckJobItem> checkJobItemList = new ArrayList<>();

            for(CheckListItem checkListItem : checkListItemList) {
                CheckJobItem checkJobItem = dataManager.create(CheckJobItem.class);
                checkJobItem.setCheckJob(checkJob);
                checkJobItem.setJobItemName(checkListItem.getChecklistItemName());
                checkJobItem.setDescription(checkListItem.getDescription());
                checkJobItem.setCheckListItemType(checkListItem.getCheckListItemType());
                checkJobItem.setCheckMethod(checkListItem.getCheckMethod());
                checkJobItem.setDescription(checkListItem.getDescription());
                checkJobItem.setCategory(checkListItem.getCategory());
                checkJobItem.setSequence(checkListItem.getSequence());
                checkJobItem.setStandardValue(checkListItem.getStandardValue());
                checkJobItem.setLowerLimitValue(checkListItem.getLowerLimitValue());
                checkJobItem.setUpperLimitValue(checkListItem.getUpperLimitValue());

                checkJobItemList.add(checkJobItem);
            }
            checkJob.setCheckJobItems(checkJobItemList);
        }
    }
}