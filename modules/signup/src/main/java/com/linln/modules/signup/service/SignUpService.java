package com.linln.modules.signup.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.activity.domain.ActivityVo;
import com.linln.modules.signup.domain.SignUp;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
public interface SignUpService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<SignUp> getPageList(Example<SignUp> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    SignUp getById(Long id);

    /**
     * 保存数据
     * @param signUp 实体对象
     */
    SignUp save(SignUp signUp);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    @Transactional
    Integer countByActivityIdAndOpenId(Long activityId, String openId);


    @Transactional
    List<SignUp> getListByOpenId(String openId);

    List<ActivityVo> getListDetailByOpenId(String openId);


}