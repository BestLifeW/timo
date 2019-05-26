package com.linln.component.thymeleaf.utility;

public class StatusUtils {

    static  int  statusList[] = {0,1,2};

    static String[] statusName ={"审核中","打卡成功","审核失败"};

    public static String getStatusName(int statu){
        for (int i : statusList) {
            if (i==statu){
                return statusName[statu];
            }
        }
        return null;
    }
}
