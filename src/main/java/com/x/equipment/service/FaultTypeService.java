package com.x.equipment.service;

import com.x.equipment.entity.CheckList;
import com.x.equipment.entity.FaultType;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("EQUI_FaultTypeService")
public class FaultTypeService extends BaseService<FaultType>{

    @Autowired
    private DataManager dataManager;

    @Override
    public String generateName() {
        return super.generateName(FaultType.class.getSimpleName());
    }

    @Override
    public FaultType loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<FaultType> faultTypeList = dataManager.load(FaultType.class).condition(PropertyCondition.equal("faultTypeCode", name)).list();
        if(faultTypeList.isEmpty())
        {
            return null;
        }
        return faultTypeList.getFirst();
    }
}