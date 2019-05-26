package com.linln.admin.wxuser.controller;

import com.linln.admin.wxuser.validator.UserValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.wxuser.domain.WxUser;
import com.linln.modules.wxuser.service.WxUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Controller
@RequestMapping("/wxuser/user")
public class WxUserController {

    @Autowired
    private WxUserService userService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("wxuser:user:index")
    public String index(Model model, WxUser user) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("remarks", match -> match.contains())
                .withMatcher("nickName", match -> match.contains());

        // 获取数据列表
        Example<WxUser> example = Example.of(user, matcher);
        Page<WxUser> list = userService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/wxuser/user/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("wxuser:user:add")
    public String toAdd() {
        return "/wxuser/user/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("wxuser:user:edit")
    public String toEdit(@PathVariable("id") WxUser user, Model model) {
        model.addAttribute("user", user);
        return "/wxuser/user/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping({"/add","/edit"})
    @RequiresPermissions({"wxuser:user:add","wxuser:user:edit"})
    @ResponseBody
    public ResultVo save(@Validated UserValid valid, WxUser user) {
        // 复制保留无需修改的数据
        if (user.getId() != null) {
            WxUser beUser = userService.getById(user.getId());
            EntityBeanUtil.copyProperties(beUser, user);
        }

        // 保存数据
        userService.save(user);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("wxuser:user:detail")
    public String toDetail(@PathVariable("id") WxUser user, Model model) {
        model.addAttribute("user",user);
        return "/wxuser/user/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("wxuser:user:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (userService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }
}