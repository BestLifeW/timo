package com.linln.modules.ClockIn.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClockinVO implements Serializable {
    private Long activityId;
    private Long clocikId;
    private String activityName;

    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date clockinDate;
    private String status;

    private String clockUrl;

}
