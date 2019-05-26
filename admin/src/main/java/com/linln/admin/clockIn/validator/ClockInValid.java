package com.linln.admin.clockIn.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author lovec
 * @date 2019/05/11
 */
@Data
public class ClockInValid implements Serializable {
    @NotEmpty(message = "OpenId不能为空")
    private String openId;
}