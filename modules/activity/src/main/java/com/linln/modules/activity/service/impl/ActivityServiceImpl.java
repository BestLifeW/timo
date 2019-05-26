package com.linln.modules.activity.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.StatusUtil;
import com.linln.modules.activity.domain.Activity;
import com.linln.modules.activity.repository.ActivityRepository;
import com.linln.modules.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public Activity getById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<Activity> getPageList(Example<Activity> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return activityRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param activity 实体对象
     */
    @Override
    public Activity save(Activity activity) {
        if (StringUtils.isEmpty(activity.getId())) {
            activity.setCreateDate(new Date());
            activity.setGmtModified(new Date());
        }else {
            activity.setGmtModified(new Date());
        }
        return activityRepository.save(activity);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return activityRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public List<Activity> getList() {
        return activityRepository.findAll();
    }
}