package com.linln.modules.activity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityVo implements Serializable {

    private Long id;
    // 备注
    private String remarks;

    // 标题
    private String caption;
    // 内容
    private String content;
    // 开始时间
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date startDate;
    // 结束时间
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date endDate;

    /**
     * 照片URL
     */
    private String photoUrl;

    /**
     * 报名日期
     */
    @JsonFormat(pattern ="yyyy-MM-dd")
    private Date signUpDate;

    /**
     * 签到次数
     */
    private Integer signUpCount;

    /**
     * 剩余的天数;
     */
    private Integer surplusDay;

    /**
     * 今天的打开状态
     */
    private Integer todayStatus;
}
