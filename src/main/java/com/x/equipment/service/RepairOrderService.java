package com.x.equipment.service;

import com.x.equipment.entity.RepairOrder;
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

@Component("EQUI_RepairOrderService")
public class RepairOrderService extends BaseService<RepairOrder> {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Sequences sequences;

    @Override
    public String generateName() {
        LocalDateTime now = LocalDateTime.now();
        String dateStr = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Long repairOrderSequence = sequences.createNextValue(Sequence.withName("REPAIR_ORDER_SEQUENCE_" + dateStr)
                .setStartValue(1)
                .setIncrement(1));

        String sequenceStr = String.format("%05d", repairOrderSequence);

        return "M" + dateStr + sequenceStr;
    }

    @Override
    public RepairOrder loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<RepairOrder> repairOrderList = dataManager.load(RepairOrder.class).condition(PropertyCondition.equal("orderNumber", name)).list();
        if(repairOrderList.isEmpty())
        {
            return null;
        }
        return repairOrderList.getFirst();
    }

    public String generateOrderNumber() {
        return this.generateName();
    }
}