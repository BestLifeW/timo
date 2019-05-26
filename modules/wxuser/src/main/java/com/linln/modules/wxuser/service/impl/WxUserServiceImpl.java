package com.linln.modules.wxuser.service.impl;

import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.modules.wxuser.domain.WxUser;
import com.linln.modules.wxuser.repository.WxUserRepository;
import com.linln.modules.wxuser.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserRepository userRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public WxUser getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<WxUser> getPageList(Example<WxUser> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return userRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param user 实体对象
     */
    @Override
    public WxUser save(WxUser user) {
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
    public WxUser getWxUserByOpenId(String openId) {
        return userRepository.getWxUserByOpenId(openId);
    }

}