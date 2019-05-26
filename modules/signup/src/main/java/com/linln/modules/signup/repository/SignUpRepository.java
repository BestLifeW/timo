package com.linln.modules.signup.repository;

import com.linln.modules.signup.domain.SignUp;
import com.linln.modules.system.repository.BaseRepository;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/08
 */
public interface SignUpRepository extends BaseRepository<SignUp, Long> {
    Integer countByActivityIdAndOpenId(Long activityId,String openId);

    List<SignUp> findAllByOpenIdOrderByCreateDateDesc(String openId);

    List<Long> findActivityIdByOpenIdOrderByCreateDateDesc(String openId);

    Integer countByActivityId(Long activityId);

    Integer countByActivityIdAndOpenIdAndStatus(Long activityId,String openId,String status);
}