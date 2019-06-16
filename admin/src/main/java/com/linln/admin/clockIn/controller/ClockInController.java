package com.linln.admin.clockIn.controller;

import com.linln.admin.clockIn.validator.ClockInValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.ClockIn.domain.ClockIn;
import com.linln.modules.ClockIn.service.ClockInService;
import com.linln.modules.wxuser.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lovec
 * @date 2019/05/11
 */
@Controller
@RequestMapping("/ClockIn/clockIn")
@Slf4j
public class ClockInController {

    @Autowired
    private ClockInService clockInService;

    @Autowired
    private WxUserService userService;

    @Value("${localhost}")
    public  String localhost;
    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("clockIn:clockIn:index")
    public String index(Model model, ClockIn clockIn) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();
        // 获取数据列表
        Example<ClockIn> example = Example.of(clockIn, matcher);
        Page<ClockIn> list = clockInService.getPageList(example);
        List<ClockIn> content = list.getContent();

        // 封装数据
        model.addAttribute("list", content);
        model.addAttribute("page", list);
        return "/ClockIn/clockIn/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("clockIn:clockIn:add")
    public String toAdd() {
        return "/ClockIn/clockIn/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("clockIn:clockIn:edit")
    public String toEdit(@PathVariable("id") ClockIn clockIn, Model model, HttpServletRequest request) {
        String s = localhost+"/upload";
        clockIn.setClockInUrl(s+clockIn.getClockInUrl());
        model.addAttribute("clockIn", clockIn);
        return "/ClockIn/clockIn/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping({"/add","/edit"})
    @RequiresPermissions({"clockIn:clockIn:add","clockIn:clockIn:edit"})
    @ResponseBody
    public ResultVo save(@Validated ClockInValid valid, ClockIn clockIn) {
        // 复制保留无需修改的数据
        if (clockIn.getId() != null) {
            ClockIn beClockIn = clockInService.getById(clockIn.getId());
            clockIn.setClockInUrl(beClockIn.getClockInUrl());
            EntityBeanUtil.copyProperties(beClockIn, clockIn);
        }

        // 保存数据
        clockInService.save(clockIn);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("clockIn:clockIn:detail")
    public String toDetail(@PathVariable("id") ClockIn clockIn, Model model,HttpServletRequest request) {
        String s = localhost+"/upload";

        clockIn.setClockInUrl(s+clockIn.getClockInUrl());
        model.addAttribute("clockIn",clockIn);
        return "/ClockIn/clockIn/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("clockIn:clockIn:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (clockInService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}