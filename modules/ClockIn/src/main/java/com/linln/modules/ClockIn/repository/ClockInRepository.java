package com.linln.modules.ClockIn.repository;

import com.linln.modules.ClockIn.domain.ClockIn;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author lovec
 * @date 2019/05/11
 */
public interface ClockInRepository extends BaseRepository<ClockIn, Long> {
    Integer countByActivityIdAndOpenIdAndClockStatus(Long activityId,String openId,Integer status);

    @Query(value = "select  * from t_clock_in t where t.activity_id = ?1 and open_id = ?2 and t.create_date >=?3 and t.create_date <= ?4 and t.clock_status=1 ",nativeQuery =true)
    ClockIn findByActivityIdAndOpenIdAndCreateDateBetween(@Param("activityId") Long activityId, @Param("openId") String openId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<ClockIn> getAllByOpenIdOrderByCreateDateDesc(String openId);

    Integer countAllByOpenIdAndClockStatus(String openId ,Integer status);
}