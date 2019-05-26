package com.linln.admin.signup.controller;

import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.signup.domain.SignUp;
import com.linln.modules.signup.service.SignUpService;
import com.linln.modules.wxuser.domain.WxUser;
import com.linln.modules.wxuser.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app/signup")
@Slf4j
public class AppSignUpController {

    private final SignUpService signUpService;

    private final WxUserService wxUserService;

    public AppSignUpController(SignUpService signUpService, WxUserService wxUserService) {
        this.signUpService = signUpService;
        this.wxUserService = wxUserService;
    }


    @RequestMapping("add")
    public ResultVo add(String openId,Long activityId){
        //判断是否报过名
        Integer count = signUpService.countByActivityIdAndOpenId(activityId, openId);
        if (count>0){
            return ResultVoUtil.error(500,"已经报名");
        }else{
            WxUser wxUserByOpenId = wxUserService.getWxUserByOpenId(openId);
            if (wxUserByOpenId==null){
                return ResultVoUtil.error(500,"用户不存在");
            }
            signUpService.save(SignUp.builder().activityId(activityId).status(StatusEnum.OK.getCode()).openId(openId).userId(wxUserByOpenId.getId()).build());
            return ResultVoUtil.success("报名成功");
        }
    }

    @ResponseBody
    @GetMapping(value = "/list")
    public ResultVo list(String openId){
        return ResultVoUtil.success(signUpService.getListDetailByOpenId(openId));
    }
}
