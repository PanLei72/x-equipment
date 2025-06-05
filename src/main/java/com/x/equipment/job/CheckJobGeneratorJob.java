package com.x.equipment.job;

import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.*;
import com.x.equipment.service.CheckJobService;
import io.jmix.core.DataManager;
import io.jmix.core.security.Authenticated;
import io.jmix.flowui.model.DataContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成设备点检、保养任务
 */
public class CheckJobGeneratorJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(CheckJobGeneratorJob.class);
    @Autowired
    private DataManager dataManager;

    @Autowired
    private CheckJobService checkJobService;


    @Authenticated
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<CheckPlan> checkPlans = dataManager.load(CheckPlan.class).all().list();
        if(checkPlans.isEmpty())
        {
            log.warn("Check plan list is empty");
            return;
        }
        for (CheckPlan checkPlan : checkPlans) {
            log.info("Check plan: {}", checkPlan);
            this.generateCheckJob(checkPlan);
            log.info("Check plan finished: {}", checkPlan);
        }
        log.info("There are {} registered users", checkPlans);
    }

    private void generateCheckJob(CheckPlan checkPlan)
    {
        EquipmentClass equipmentClass = checkPlan.getEquipmentClass();
        List<Equipment> equipmentList = equipmentClass.getEquipments();

        if(equipmentList.isEmpty())
        {
            return;
        }
        CheckList checkList = checkPlan.getChecklist();
        List<CheckListItem> checkListItemList = checkList.getCheckListItems();
        if(checkListItemList == null || checkListItemList.isEmpty())
        {
            return;
        }

        for (Equipment equipment : equipmentList) {
            String jobName = checkJobService.generateJobName();

            CheckJob checkJob = dataManager.create(CheckJob.class);
            checkJob.setJobName(jobName);
            checkJob.setDescription(checkPlan.getDescription());
            checkJob.setEquipment(equipment);
            checkJob.setCheckCycle(checkPlan.getCheckCycle());
            checkJob.setCheckCycleUnit(checkPlan.getCheckCycleUnit());
            checkJob.setChecklist(checkPlan.getChecklist());
            checkJob.setJobStatus(JobStatus.CREATED);
            checkJob.setPlanTime(LocalDateTime.now());

            List<CheckJobItem> checkJobItemList = new ArrayList<>();
            for(CheckListItem checkListItem : checkListItemList) {
                CheckJobItem checkJobItem = dataManager.create(CheckJobItem.class);
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
                checkJobItem.setCheckJob(checkJob);

                checkJobItemList.add(checkJobItem);

            }

            checkJob.setCheckJobItems(checkJobItemList);

            dataManager.save(checkJob);
        }

    }
}
