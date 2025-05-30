package com.x.equipment.service;

import com.x.equipment.entity.CheckList;
import com.x.equipment.entity.EquipmentLevel;
import com.x.equipment.entity.FaultLevel;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("EQUI_FaultLevelService")
public class FaultLevelService extends BaseService<FaultLevel>{

    @Autowired
    private DataManager dataManager;

    @Override
    public String generateName() {
        return super.generateName(FaultLevel.class.getSimpleName());
    }

    @Override
    public FaultLevel loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<FaultLevel> faultLevelList = dataManager.load(FaultLevel.class).condition(PropertyCondition.equal("faultLevelCode", name)).list();
        if(faultLevelList.isEmpty())
        {
            return null;
        }
        return faultLevelList.getFirst();
    }
}