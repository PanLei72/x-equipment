package com.x.equipment.service;

import com.x.equipment.constants.CheckCycleUnit;
import com.x.equipment.constants.JobStatus;
import com.x.equipment.entity.*;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.LogicalCondition;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component("EQUI_CheckJobService")
public class CheckJobService extends BaseService<CheckJob> {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Sequences sequences;

    private static final Logger log = LoggerFactory.getLogger(CheckJobService.class);


    @Override
    public String generateName() {
        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Long checkJobSequence = sequences.createNextValue(Sequence.withName("CHECK_JOB_SEQUENCE_" + dateStr)
                .setStartValue(1)
                .setIncrement(1));

        String sequenceStr = String.format("%05d", checkJobSequence);

        return "J" + dateStr + sequenceStr;
    }

    @Override
    public CheckJob loadObjectByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        List<CheckJob> checkJobList = dataManager.load(CheckJob.class).condition(PropertyCondition.equal("jobName", name)).list();
        if (checkJobList.isEmpty()) {
            return null;
        }
        return checkJobList.getFirst();
    }

    public String generateJobName() {
        return this.generateName();
    }

    /**
     * 根据维护的点检保养计划生成检查任务
     * @param checkPlan
     */
    public void generateCheckJob(CheckPlan checkPlan) {
        EquipmentClass equipmentClass = checkPlan.getEquipmentClass();
        List<Equipment> equipmentList = equipmentClass.getEquipments();

        if (equipmentList.isEmpty()) {
            return;
        }
        CheckList checkList = checkPlan.getChecklist();
        List<CheckListItem> checkListItemList = checkList.getCheckListItems();
        if (checkListItemList == null || checkListItemList.isEmpty()) {
            log.warn("检查任务{}，未配置检查项", checkPlan.getCheckPlanName());
            return;
        }

        Integer checkCycle = checkPlan.getCheckCycle();
        CheckCycleUnit checkCycleUnit = checkPlan.getCheckCycleUnit();

        for (Equipment equipment : equipmentList) {
            CheckJob existsCheckJob= this.getEquipmentLastCheckJob(equipment, checkList);
            if(existsCheckJob != null)
            {
                if(JobStatus.CREATED.equals(existsCheckJob.getJobStatus()))
                {
                    log.warn("设备{}，检查清单{}，已存在检查任务，不再生成新的检查任务", equipment.getEquipmentName(), checkList.getCheckListName());
                    continue;
                }
            }

            LocalDateTime lastPlanStartTime = LocalDate.now().atStartOfDay();
            if(existsCheckJob != null)
            {
                lastPlanStartTime = existsCheckJob.getPlanStartTime();
            }

            LocalDateTime planStartTime = getLocalDateTime(lastPlanStartTime, checkCycleUnit, checkCycle);
            LocalDateTime planCompleteTime = planStartTime.toLocalDate().atTime(23, 59, 59, 999999999);

            String jobName = this.generateJobName();

            CheckJob checkJob = dataManager.create(CheckJob.class);
            checkJob.setJobName(jobName);
            checkJob.setDescription(checkPlan.getDescription());
            checkJob.setCategory(checkPlan.getCategory());
            checkJob.setEquipment(equipment);
            checkJob.setCheckCycle(checkPlan.getCheckCycle());
            checkJob.setCheckCycleUnit(checkPlan.getCheckCycleUnit());
            checkJob.setChecklist(checkPlan.getChecklist());
            checkJob.setJobStatus(JobStatus.CREATED);
            checkJob.setPlanStartTime(planStartTime);
            checkJob.setPlanCompleteTime(planCompleteTime);

            List<CheckJobItem> checkJobItemList = new ArrayList<>();
            for (CheckListItem checkListItem : checkListItemList) {
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

    private LocalDateTime getLocalDateTime(LocalDateTime lastPlanStartTime, CheckCycleUnit checkCycleUnit, Integer checkCycle) {
        LocalDateTime planStartTime = lastPlanStartTime;
        if(CheckCycleUnit.DAY.equals(checkCycleUnit)) {
            planStartTime = lastPlanStartTime.plusDays(checkCycle);
        } else if (CheckCycleUnit.WEEK.equals(checkCycleUnit)) {
            planStartTime = lastPlanStartTime.plusWeeks(checkCycle);
        } else if (CheckCycleUnit.MONTH.equals(checkCycleUnit)) {
            planStartTime = lastPlanStartTime.plusMonths(checkCycle);
        } else if (CheckCycleUnit.YEAR.equals(checkCycleUnit)) {
            planStartTime = lastPlanStartTime.plusYears(checkCycle);
        }
        return planStartTime;
    }

    /**
     * 获取设备最后点检/保养任务
     * @param equipment
     * @return
     */
    public CheckJob getEquipmentLastCheckJob(Equipment equipment, CheckList checkList)
    {
        List<CheckJob> checkJobList = dataManager.load(CheckJob.class)
                .condition(
                        LogicalCondition.and(
                                PropertyCondition.equal("checklist", checkList),
                                PropertyCondition.equal("equipment", equipment)
                        )
                )
                .sort(Sort.by(Sort.Direction.DESC, "planStartTime"))
                .firstResult(1)
                .maxResults(1)
                .list();

        if(!checkJobList.isEmpty())
        {
            return checkJobList.getFirst();
        }
        return null;
    }
}