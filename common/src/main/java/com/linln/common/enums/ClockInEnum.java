package com.linln.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 打卡状态 枚举
 *
 * @author lovec
 */
@Getter
public enum ClockInEnum {

    REVIEW(0, "审核中"),
    PASS(1, "打卡成功"),
    NOPASS(2, "审核失败");

    /**
     * 状态
     */
    private int status;

    /**
     * 状态名称
     */
    private String statusName;

    ClockInEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public  static  ClockInEnum getStatusNameByStats(int status){
        return Arrays.stream(values()).filter(i->i.getStatus()==status).findFirst().orElse(null);
    }


}
