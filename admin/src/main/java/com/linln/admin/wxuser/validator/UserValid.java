package com.linln.admin.wxuser.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Data
public class UserValid implements Serializable {
    @NotEmpty(message = "手机号码不能为空")
    private String userPhone;
    @NotEmpty(message = "OpenId不能为空")
    private String openId;
}