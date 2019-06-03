package com.linln.modules.ClockIn.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.ClockIn.domain.ClockIn;
import com.linln.modules.ClockIn.domain.ClockinVO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/11
 */
public interface ClockInService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<ClockIn> getPageList(Example<ClockIn> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    ClockIn getById(Long id);

    /**
     * 保存数据
     * @param clockIn 实体对象
     */
    ClockIn save(ClockIn clockIn);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);

    /**
     *  判断今天是否已经打卡
     * @param activityId
     * @param openId
     * @return
     */
    ClockIn checkTodayIsClockIn(Long activityId,String openId,String date);

    List<ClockinVO> getAllByOpenId(String openId);
}