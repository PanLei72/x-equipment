package com.x.equipment.service;

import com.x.equipment.entity.CheckList;
import com.x.equipment.entity.CheckListItem;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("EQUI_CheckListItemService")
public class CheckListItemService extends BaseService<CheckListItem>{

    @Autowired
    private DataManager dataManager;

    @Override
    public String generateName() {
        return super.generateName(CheckListItem.class.getSimpleName());
    }
    

    @Override
    public CheckListItem loadObjectByName(String name) {
        if(StringUtils.isEmpty(name))
        {
            return null;
        }
        List<CheckListItem> checkListItemList = dataManager.load(CheckListItem.class).condition(PropertyCondition.equal("checklistItemName", name)).list();
        if(checkListItemList.isEmpty())
        {
            return null;
        }
        return checkListItemList.getFirst();
    }
}