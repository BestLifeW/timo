package com.linln.modules.signup.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.DateUtil;
import com.linln.modules.ClockIn.domain.ClockIn;
import com.linln.modules.ClockIn.repository.ClockInRepository;
import com.linln.modules.ClockIn.service.ClockInService;
import com.linln.modules.activity.domain.Activity;
import com.linln.modules.activity.domain.ActivityVo;
import com.linln.modules.activity.repository.ActivityRepository;
import com.linln.modules.signup.domain.SignUp;
import com.linln.modules.signup.repository.SignUpRepository;
import com.linln.modules.signup.service.SignUpService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private SignUpRepository signUpRepository;

    @Autowired
    private ActivityRepository activityRepository;


    @Autowired
    private ClockInRepository clockInRepository;

    @Autowired
    private ClockInService clockInService;

    /**
     * 根据ID查询数据
     *
     * @param id 主键ID
     */
    @Override
    @Transactional
    public SignUp getById(Long id) {
        return signUpRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     *
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<SignUp> getPageList(Example<SignUp> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return signUpRepository.findAll(example, page);
    }

    /**
     * 保存数据
     *
     * @param signUp 实体对象
     */
    @Override
    public SignUp save(SignUp signUp) {
        return signUpRepository.save(signUp);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return signUpRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    @Transactional
    public Integer countByActivityIdAndOpenId(Long activityId, String openId) {
        return signUpRepository.countByActivityIdAndOpenId(activityId, openId);
    }

    @Override
    public List<SignUp> getListByOpenId(String openId) {
        return signUpRepository.findAllByOpenIdOrderByCreateDateDesc(openId);
    }

    @Override
    public List<ActivityVo> getListDetailByOpenId(String openId) {
        List<ActivityVo> resList = Lists.newArrayList();
        List<SignUp> findAll = signUpRepository.findAllByOpenIdOrderByCreateDateDesc(openId);
        for (SignUp signUp : findAll) {
            Activity one = activityRepository.getOne(signUp.getActivityId());
            Integer clockinNum = clockInRepository.countByActivityIdAndOpenIdAndClockStatus(one.getId(), openId, 1);
            ClockIn clockIn = clockInService.checkTodayIsClockIn(one.getId(), openId, DateUtil.getToday());
            ActivityVo.ActivityVoBuilder activityVoBuilder = ActivityVo.builder().caption(one.getCaption()).content(one.getContent()).endDate(one.getEndDate())
                    .photoUrl(one.getPhotoUrl()).remarks(one.getRemarks()).id(one.getId())
                    .signUpDate(signUp.getCreateDate()).surplusDay(DateUtil.differentDaysByMillisecond(new Date(), one.getEndDate())).signUpCount(clockinNum).todayStatus(clockIn == null ? null : clockIn.getClockStatus());
            resList.add(activityVoBuilder.build());
        }
        return resList;
    }


}