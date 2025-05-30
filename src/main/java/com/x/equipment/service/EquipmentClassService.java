package com.x.equipment.service;

import com.x.equipment.entity.EquipmentClass;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("EQUI_EquipmentClassService")
public class EquipmentClassService extends BaseService<EquipmentClass>{

    @Autowired
    private DataManager dataManager;

    @Override
    public String generateName() {
        return super.generateName(EquipmentClass.class.getSimpleName());
    }

    @Override
    public EquipmentClass loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<EquipmentClass> equipmentClassList = dataManager.load(EquipmentClass.class).condition(PropertyCondition.equal("equipmentClassName", name)).list();
        if(equipmentClassList.isEmpty())
        {
            return null;
        }
        return equipmentClassList.getFirst();
    }
}