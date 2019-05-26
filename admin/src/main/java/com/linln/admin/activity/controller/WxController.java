package com.linln.admin.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.linln.common.config.WxConfig;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.CommonUtil;
import com.linln.modules.wxuser.domain.WxUser;
import com.linln.modules.wxuser.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app/wx")
public class WxController {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private WxUserService  wxUserService;


    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET) // 获取用户信息
    public String getOpenId(@Param("code") String code, @RequestParam("headurl") String headurl,
                            @RequestParam("nickname") String nickname, @RequestParam("sex") String sex,
                            @RequestParam("country") String country, @RequestParam("province") String province,
                            @RequestParam("city") String city) {
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        try {
            if (StringUtils.isEmpty(code)) {
                System.out.println("code为空");
            } else {
                String requestUrl = WX_URL.replace("APPID", wxConfig.APPID).replace("SECRET", wxConfig.APPSECRECT)
                        .replace("JSCODE", code).replace("authorization_code", wxConfig.GRANTTYPE);
                JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
                if (jsonObject != null) {
                    try {
                        // 业务操作
                        String openid = jsonObject.getString("openid");
                        System.out.println(jsonObject);
                        System.out.println(openid);
                        Integer integer = wxUserService.countByOpenId(openid);
                        if (!(integer>0)){
                            WxUser build = WxUser.builder()
                                    .openId(openid).city(city)
                                    .country(country).sex(sex)
                                    .nickName(nickname).province(province)
                                    .userHeadUrl(headurl)
                                    .status(StatusEnum.OK.getCode())
                                    .build();
                            System.out.println(build);
                            wxUserService.save(build);
                        }
                        return openid;
                    } catch (Exception e) {
                        System.out.println("业务操作失败");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("code无效");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "错误";
    }

}
