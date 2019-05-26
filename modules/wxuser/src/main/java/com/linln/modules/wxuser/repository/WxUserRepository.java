package com.linln.modules.wxuser.repository;

import com.linln.modules.system.repository.BaseRepository;
import com.linln.modules.wxuser.domain.WxUser;

/**
 * @author lovec
 * @date 2019/05/08
 */
public interface WxUserRepository extends BaseRepository<WxUser, Long> {

    Integer countByOpenId(String openId);


    WxUser getWxUserByOpenId(String openId);
}