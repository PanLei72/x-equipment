package com.x.equipment.service;

import com.x.equipment.ComponentUtilities;
import org.springframework.stereotype.Component;

@Component("EQUI_BaseService")
public abstract class BaseService implements IBaseService{
    private int cnt = 0;

    protected String generateName(String sampleName) {
        String objectName = sampleName + ++this.cnt;
        try {
            while (loadObjectByName(objectName) != null)
                objectName = sampleName + ++this.cnt;
        } catch (Throwable throwable) {
            objectName = ComponentUtilities.generateName(sampleName);
        }
        return objectName;
    }

}