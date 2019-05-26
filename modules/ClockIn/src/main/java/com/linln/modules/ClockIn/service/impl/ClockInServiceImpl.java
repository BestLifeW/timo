package com.linln.modules.ClockIn.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.modules.ClockIn.domain.ClockIn;
import com.linln.modules.ClockIn.repository.ClockInRepository;
import com.linln.modules.ClockIn.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/11
 */
@Service
public class ClockInServiceImpl implements ClockInService {

    @Autowired
    private ClockInRepository clockInRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public ClockIn getById(Long id) {
        return clockInRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<ClockIn> getPageList(Example<ClockIn> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return clockInRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param clockIn 实体对象
     */
    @Override
    public ClockIn save(ClockIn clockIn) {
        return clockInRepository.save(clockIn);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return clockInRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public ClockIn  checkTodayIsClockIn(Long activityId, String openId,String date) {

        return clockInRepository.findByActivityIdAndOpenIdAndCreateDateBetween(activityId, openId, date + " 00:00:00", date + " 23:59:59");

    }
}