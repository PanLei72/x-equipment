package com.x.equipment.service;

import com.x.equipment.entity.CheckJob;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component("EQUI_CheckJobService")
public class CheckJobService extends BaseService<CheckJob>{

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Sequences sequences;

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
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<CheckJob> checkJobList = dataManager.load(CheckJob.class).condition(PropertyCondition.equal("jobName", name)).list();
        if(checkJobList.isEmpty())
        {
            return null;
        }
        return checkJobList.getFirst();
    }

    public String generateJobName()
    {
        return this.generateName();
    }
}