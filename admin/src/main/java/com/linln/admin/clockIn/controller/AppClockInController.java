package com.linln.admin.clockIn.controller;

import com.linln.common.config.FileResourceConfig;
import com.linln.common.enums.ClockInEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.DateUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.ClockIn.domain.ClockIn;
import com.linln.modules.ClockIn.domain.ClockinVO;
import com.linln.modules.ClockIn.service.ClockInService;
import com.linln.modules.activity.domain.Activity;
import com.linln.modules.activity.service.ActivityService;
import com.linln.modules.wxuser.domain.TUser;
import com.linln.modules.wxuser.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("app/clockin")
public class AppClockInController {

    @Autowired
    private ClockInService clockInService;


    @Autowired
    private FileResourceConfig fileResourceConfig;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private ActivityService activityService;
    @GetMapping("/todayisclockin/{activityId}/{openId}")
    public ResultVo todayisclockin (@PathVariable(value = "activityId") Long activityId,@PathVariable(value = "openId") String openId){
        ClockIn clockIn = clockInService.checkTodayIsClockIn(activityId, openId, DateUtil.getToday());
        return ResultVoUtil.success(clockIn==null?null:clockIn.getClockStatus());
    }


    @PostMapping("/add")
    public ResultVo add(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        log.info("即将进行图片上传工作{}", file.getSize());
        Long activityId = Long.valueOf(request.getParameter("activityId"));
        String openId = request.getParameter("openId");
        log.info("接受到activityId{}", activityId);
        log.info("接受到openId{}", openId);
        ClockIn clockIn = clockInService.checkTodayIsClockIn(activityId, openId, DateUtil.getToday());
        if (clockIn!=null){
            return ResultVoUtil.error("今天已经打卡");
        }
        Activity activity = activityService.getById(activityId);
        Date endDate = activity.getEndDate();
        if (System.currentTimeMillis()>endDate.getTime()){
            return ResultVoUtil.error("活动已过期");
        }
        //判断是否过期
        if (!file.isEmpty()) {
            log.info("成功获取照片");
            String fileName = openId+"_"+activityId+"_";
            String path;
            String type;
            String originalFilename = file.getOriginalFilename();
            type = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : null;
            log.info("图片初始名称为：" + fileName + " 类型为：" + type);
            if (type != null) {
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
                    //String realPath = request.getSession().getServletContext().getRealPath("/");
                    String realPath = fileResourceConfig.uploadDir;
                    log.info("接收到文件路径：{}",realPath);
                    // 自定义的文件名称
                    String trueFileName = fileName+System.currentTimeMillis();
                    // 设置存放图片文件的路径
                    String filePath = fileResourceConfig.clockinPhotoDir + trueFileName + "." + type;
                    path = realPath + filePath;
                    File newFile = new File(path);
                    File fileParent = newFile.getParentFile();
                    if(!fileParent.exists()){
                        fileParent.mkdirs();
                    }
                    log.info("存放图片文件的路径:" + path);
                    file.transferTo(new File(path));
                    log.info("文件成功上传到指定目录下");

                    TUser tUserByOpenId = wxUserService.getWxUserByOpenId(openId);
                    //保存数据
                    ClockIn build = ClockIn.builder().activityId(activityId).openId(openId)
                            .clockInUrl(filePath).createDate(new Date())
                            .updateDate(new Date()).status(StatusEnum.OK.getCode())
                            .clockStatus(ClockInEnum.REVIEW.getStatus())
                            .nickName(tUserByOpenId ==null?null: tUserByOpenId.getNickName())
                            .build();
                    clockInService.save(build);
                } else {
                    log.info("不是我们想要的文件类型,请按要求重新上传");
                    return ResultVoUtil.error("只能上传GIF,PNG,JPG类型的图片");
                }
            } else {
                log.info("文件类型为空");
                return ResultVoUtil.error("找不到文件的后缀名");
            }
        } else {
            log.info("没有找到相对应的文件");
            return ResultVoUtil.error("没有找到图片");
        }
        return ResultVoUtil.success("签到成功");
    }
    @GetMapping("getAll/{openId}")
    public ResultVo getAllByOpenId(@PathVariable("openId") String openId){
        Stream<ClockinVO> limit = clockInService.getAllByOpenId(openId).stream().limit(30);
        return ResultVoUtil.success(limit.collect(Collectors.toList()));
    }

}


