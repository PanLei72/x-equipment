package com.x.equipment.service;

import com.x.equipment.entity.CheckList;
import com.x.equipment.entity.CheckPlan;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("EQUI_CheckPlanService")
public class CheckPlanService extends BaseService<CheckPlan>{

    @Autowired
    private DataManager dataManager;

    @Override
    public String generateName() {
        return super.generateName(CheckPlan.class.getSimpleName());
    }

    @Override
    public CheckPlan loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<CheckPlan> checkPlanList = dataManager.load(CheckPlan.class).condition(PropertyCondition.equal("checkPlanName", name)).list();
        if(checkPlanList.isEmpty())
        {
            return null;
        }
        return checkPlanList.getFirst();
    }
}