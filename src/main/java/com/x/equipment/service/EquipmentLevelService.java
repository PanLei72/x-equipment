package com.x.equipment.service;

import com.x.equipment.entity.CheckList;
import com.x.equipment.entity.EquipmentLevel;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("EQUI_EquipmentLevelService")
public class EquipmentLevelService extends BaseService<EquipmentLevel>{

    @Autowired
    private DataManager dataManager;

    @Override
    public String generateName() {
        return super.generateName(EquipmentLevel.class.getSimpleName());
    }

    @Override
    public EquipmentLevel loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<EquipmentLevel> equipmentLevelList = dataManager.load(EquipmentLevel.class).condition(PropertyCondition.equal("levelName", name)).list();
        if(equipmentLevelList.isEmpty())
        {
            return null;
        }
        return equipmentLevelList.getFirst();
    }
}