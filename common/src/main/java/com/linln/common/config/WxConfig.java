package com.linln.common.config;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
public class WxConfig {


    @Value("${wx.config.APPID}")
    public  String APPID;

    @Value("${wx.config.APPSECRECT}")
    public  String APPSECRECT;

    @Value("${wx.config.GRANTTYPE}")
    public  String GRANTTYPE;
}
