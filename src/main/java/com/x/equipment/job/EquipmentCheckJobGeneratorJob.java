package com.x.equipment.job;

import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.security.Authenticated;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class EquipmentCheckJobGeneratorJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(EquipmentCheckJobGeneratorJob.class);
    @Autowired
    private DataManager dataManager;

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
            EquipmentCheckJob equipmentCheckJob = dataManager.create(EquipmentCheckJob.class);
            equipmentCheckJob.setJobName(LocalDateTime.now().toString());
            equipmentCheckJob.setDescription(checkPlan.getDescription());
            equipmentCheckJob.setEquipment(equipment);
            equipmentCheckJob.setCheckCycle(checkPlan.getCheckCycle());
            equipmentCheckJob.setCheckCycleUnit(checkPlan.getCheckCycleUnit());
            equipmentCheckJob.setChecklist(checkPlan.getChecklist());
            equipmentCheckJob.setJobStatus(JobStatus.NEW);
            equipmentCheckJob.setPlanTime(LocalDateTime.now());

            dataManager.save(equipmentCheckJob);

            for(CheckListItem checkListItem : checkListItemList) {
                EquipmentCheckJobItem equipmentCheckJobItem = dataManager.create(EquipmentCheckJobItem.class);
                equipmentCheckJobItem.setJobItemName(checkListItem.getChecklistItemName());
                equipmentCheckJobItem.setDescription(checkListItem.getDescription());
                equipmentCheckJobItem.setCheckListItemType(checkListItem.getCheckListItemType());
                equipmentCheckJobItem.setCheckMethod(checkListItem.getCheckMethod());
                equipmentCheckJobItem.setDescription(checkListItem.getDescription());
                equipmentCheckJobItem.setCategory(checkListItem.getCategory());
                equipmentCheckJobItem.setSequence(checkListItem.getSequence());
                equipmentCheckJobItem.setStandardValue(checkListItem.getStandardValue());
                equipmentCheckJobItem.setLowerLimitValue(checkListItem.getLowerLimitValue());
                equipmentCheckJobItem.setUpperLimitValue(checkListItem.getUpperLimitValue());
                equipmentCheckJobItem.setEquipmentCheckJob(equipmentCheckJob);

                dataManager.save(equipmentCheckJobItem);
            }

        }

    }
}
