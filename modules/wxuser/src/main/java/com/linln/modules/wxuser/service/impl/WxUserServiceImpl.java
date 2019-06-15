package com.linln.modules.wxuser.service.impl;

import com.google.common.collect.Maps;
import com.linln.common.data.PageSort;
import com.linln.common.enums.ClockInEnum;
import com.linln.common.enums.StatusEnum;
import com.linln.modules.ClockIn.repository.ClockInRepository;
import com.linln.modules.activity.repository.ActivityRepository;
import com.linln.modules.signup.repository.SignUpRepository;
import com.linln.modules.wxuser.domain.TUser;
import com.linln.modules.wxuser.repository.WxUserRepository;
import com.linln.modules.wxuser.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserRepository userRepository;

    @Autowired
    private SignUpRepository signUpRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ClockInRepository clockInRepository;
    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public TUser getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<TUser> getPageList(Example<TUser> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return userRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param user 实体对象
     */
    @Override
    public TUser save(TUser user) {
        return userRepository.saveAndFlush(user);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return userRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public Integer countByOpenId(String openId) {
        return userRepository.countByOpenId(openId);
    }

    @Override
    public TUser getWxUserByOpenId(String openId) {
        return userRepository.getWxUserByOpenId(openId);
    }

    @Override
    public Map<String, String> getUserInfo(String openId) {
        HashMap<String,String> resMap = Maps.newHashMap();
        Integer allActivity = activityRepository.countAllByIsPublish(1);
        Integer signCount = signUpRepository.countDistinctByOpenId(openId);
        Integer allClockIn = clockInRepository.countAllByOpenIdAndClockStatus(openId, ClockInEnum.PASS.getStatus());
        resMap.put("signCount",null==signCount?"0":signCount.toString());
        resMap.put("allClockIn",null==allClockIn?"0":allClockIn.toString());
        resMap.put("allActivity",null==allActivity?"0":allActivity.toString());
        return resMap;
    }


}