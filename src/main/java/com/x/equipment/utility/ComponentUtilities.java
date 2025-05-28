package com.x.equipment.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 *
 * @version 1.0.0
 * @author PanLei
 * @createTime 2023-02-16
 */
public class ComponentUtilities {


    public static String generateName(Class<? extends Object> paramClass) {
        return generateName(paramClass.getSimpleName());
    }

    public static String generateName(String simpleName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        return simpleName + "-" + simpleDateFormat.format(new Date());
    }

    
}
