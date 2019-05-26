package com.linln.admin.signup.controller;

import com.linln.admin.signup.validator.SignUpValid;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.EntityBeanUtil;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.utils.StatusUtil;
import com.linln.common.vo.ResultVo;
import com.linln.modules.signup.domain.SignUp;
import com.linln.modules.signup.service.SignUpService;
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
@RequestMapping("/signup/signUp")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    /**
     * 列表页面
     */
    @GetMapping("/index")
    @RequiresPermissions("signup:signUp:index")
    public String index(Model model, SignUp signUp) {

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();

        // 获取数据列表
        Example<SignUp> example = Example.of(signUp, matcher);
        Page<SignUp> list = signUpService.getPageList(example);

        // 封装数据
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/signup/signUp/index";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    @RequiresPermissions("signup:signUp:add")
    public String toAdd() {
        return "/signup/signUp/add";
    }

    /**
     * 跳转到编辑页面
     */
    @GetMapping("/edit/{id}")
    @RequiresPermissions("signup:signUp:edit")
    public String toEdit(@PathVariable("id") SignUp signUp, Model model) {
        model.addAttribute("signUp", signUp);
        return "/signup/signUp/add";
    }

    /**
     * 保存添加/修改的数据
     * @param valid 验证对象
     */
    @PostMapping({"/add","/edit"})
    @RequiresPermissions({"signup:signUp:add","signup:signUp:edit"})
    @ResponseBody
    public ResultVo save(@Validated SignUpValid valid, SignUp signUp) {
        // 复制保留无需修改的数据
        if (signUp.getId() != null) {
            SignUp beSignUp = signUpService.getById(signUp.getId());
            EntityBeanUtil.copyProperties(beSignUp, signUp);
        }

        // 保存数据
        signUpService.save(signUp);
        return ResultVoUtil.SAVE_SUCCESS;
    }

    /**
     * 跳转到详细页面
     */
    @GetMapping("/detail/{id}")
    @RequiresPermissions("signup:signUp:detail")
    public String toDetail(@PathVariable("id") SignUp signUp, Model model) {
        model.addAttribute("signUp",signUp);
        return "/signup/signUp/detail";
    }

    /**
     * 设置一条或者多条数据的状态
     */
    @RequestMapping("/status/{param}")
    @RequiresPermissions("signup:signUp:status")
    @ResponseBody
    public ResultVo status(
            @PathVariable("param") String param,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态
        StatusEnum statusEnum = StatusUtil.getStatusEnum(param);
        if (signUpService.updateStatus(statusEnum, ids)) {
            return ResultVoUtil.success(statusEnum.getMessage() + "成功");
        } else {
            return ResultVoUtil.error(statusEnum.getMessage() + "失败，请重新操作");
        }
    }




}