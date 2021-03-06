package com.linln.modules.activity.service;

import com.linln.common.enums.StatusEnum;
import com.linln.modules.activity.domain.Activity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
public interface ActivityService {

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    Page<Activity> getPageList(Example<Activity> example);

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    Activity getById(Long id);

    /**
     * 保存数据
     * @param activity 实体对象
     */
    Activity save(Activity activity);

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Transactional
    Boolean updateStatus(StatusEnum statusEnum, List<Long> idList);


    List<Activity> getList();
}