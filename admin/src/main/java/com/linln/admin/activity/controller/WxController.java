package com.linln.admin.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.linln.common.config.WxConfig;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.CommonUtil;
import com.linln.modules.wxuser.domain.TUser;
import com.linln.modules.wxuser.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app/wx")
@Slf4j
public class WxController {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private WxUserService  wxUserService;
    //转化emoji工具类
    private static EmojiConverter emojiConverter = EmojiConverter.getInstance();


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
                log.info("接收到微信回调{}",jsonObject.toJSONString());
                if (jsonObject != null) {
                    try {
                        // 业务操作
                        String openid = jsonObject.getString("openid");
                        Integer integer = wxUserService.countByOpenId(openid);
                        if (!(integer>0)){
                            String toAlias = emojiConverter.toAlias(nickname);
                            TUser build = TUser.builder()
                                    .openId(openid).city(city)
                                    .country(country).sex(sex)
                                    .nickName(toAlias).province(province)
                                    .userHeadUrl(headurl)
                                    .status(StatusEnum.OK.getCode())
                                    .build();
                            wxUserService.save(build);
                        }
                        return openid;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    log.info("接收到的Code无效{}",requestUrl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "错误";
    }

}
