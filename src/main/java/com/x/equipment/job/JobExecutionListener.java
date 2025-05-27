package com.x.equipment.job;

import io.jmix.core.DataManager;
import io.jmix.core.security.Authenticated;
import jakarta.annotation.PostConstruct;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExecutionListener extends JobListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobExecutionListener.class);
    @Autowired
    private DataManager dataManager;

    @Autowired
    private Scheduler scheduler;
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @PostConstruct
    private void registerListener() {
        try {
            scheduler.getListenerManager().addJobListener(this);
        } catch (SchedulerException e) {
            log.error("Cannot register job listener", e);
        }
    }

    @Authenticated
    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        log.info("jobWasExecuted: name={}, context={}",
                context.getJobDetail().getKey().getName(), context);
    }
}
