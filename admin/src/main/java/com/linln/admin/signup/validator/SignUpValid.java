package com.linln.admin.signup.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Data
public class SignUpValid implements Serializable {
    @NotNull(message = "活动ID不能为空")
    private Long activityId;
}