package com.linln.admin.activity.controller;

import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * @author lovec
 */
@RestController
@RequestMapping("app/activity")
public class AppActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 查询全部记录
     *
     * @return SysDictTypeEntity
     */
    @PostMapping(value = "/list")
    public ResultVo list() {
        return ResultVoUtil.success(activityService.getList().stream()
                .filter(i -> i.getIsPublish()==1).collect(Collectors.toList()));

    }

    @GetMapping(value = "/get/{id}")
    public ResultVo get(@PathVariable(value = "id") long id){
        return  ResultVoUtil.success(activityService.getById(id));
    }
}
