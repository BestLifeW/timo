package com.linln.admin.activity.controller;

import com.linln.admin.activity.validator.ActivityValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.activity.domain.Activity;
import com.linln.modules.activity.service.ActivityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Controller
@RequestMapping("/activity/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("activity:activity:index")
    public String index(Model model, Activity activity) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("remarks", match -> match.contains())
                .withMatcher("caption", match -> match.contains())
                .withMatcher("content", match -> match.contains());

        // 获取数据列表
        Example<Activity> example = Example.of(activity, matcher);
        Page<Activity> list = activityService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/activity/activity/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("activity:activity:add")
    public String toAdd() {
        return "/activity/activity/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("activity:activity:edit")
    public String toEdit(@PathVariable("id") Activity activity, Model model) {
        model.addAttribute("activity", activity);
        return "/activity/activity/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping({"/add","/edit"})
    @RequiresPermissions({"activity:activity:add","activity:activity:edit"})
    @ResponseBody
    public ResultVo save(@Validated ActivityValid valid, Activity activity) {
        // 复制保留无需修改的数据
        if (activity.getId() != null) {
            Activity beActivity = activityService.getById(activity.getId());
            if (StringUtils.isEmpty(activity.getPhotoUrl())){
                activity.setPhotoUrl(beActivity.getPhotoUrl());
            }
            EntityBeanUtil.copyProperties(beActivity, activity);
        }

        // 保存数据
        activityService.save(activity);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("activity:activity:detail")
    public String toDetail(@PathVariable("id") Activity activity, Model model) {
        model.addAttribute("activity",activity);
        return "/activity/activity/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("activity:activity:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (activityService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}