package com.linln.modules.ClockIn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClockinDTO implements Serializable {

    private Long activityId;

    private String openId;

    private Long userId;


}
