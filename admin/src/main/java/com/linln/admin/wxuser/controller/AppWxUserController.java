package com.linln.admin.wxuser.controller;

import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.wxuser.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/app/wxuser")
@RestController
@Slf4j
public class AppWxUserController {

    @Autowired
    private WxUserService wxUserService;

    @GetMapping("getUserinfo")
    public ResultVo getUserinfo(String openId ) {
        return ResultVoUtil.success(wxUserService.getUserInfo(openId));
    }
}
