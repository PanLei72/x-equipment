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
            this.checkJobService.generateCheckJob(checkPlan);
            log.info("Check plan finished: {}", checkPlan);
        }
        log.info("There are {} registered users", checkPlans);
    }

}
