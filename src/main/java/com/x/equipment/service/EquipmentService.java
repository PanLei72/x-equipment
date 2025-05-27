package com.x.equipment.service;

import com.x.equipment.entity.Equipment;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("EQUI_EquipmentService")
public class EquipmentService extends BaseService{

    @Autowired
    private DataManager dataManager;

    @Override
    public String generateName() {
        return super.generateName(Equipment.class.getSimpleName());
    }

    @Override
    public Object loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<Equipment> equipmentList = dataManager.load(Equipment.class).condition(PropertyCondition.equal("equipmentName", name)).list();
        if(equipmentList.isEmpty())
        {
            return null;
        }
        return equipmentList.get(0);
    }
}