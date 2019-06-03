package com.linln.modules.wxuser.repository;

import com.linln.modules.system.repository.BaseRepository;
import com.linln.modules.wxuser.domain.TUser;

/**
 * @author lovec
 * @date 2019/05/08
 */
public interface WxUserRepository extends BaseRepository<TUser, Long> {

    Integer countByOpenId(String openId);


    TUser getWxUserByOpenId(String openId);
}