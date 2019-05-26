package com.linln.modules.ClockIn.domain;

import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.StatusUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lovec
 * @date 2019/05/11
 */
@Data
@Entity
@Table(name="t_clock_in")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.notDelete)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClockIn implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // OpenId
    private String openId;
    // 用户ID
    private Long userId;
    // 打卡时间
    @CreatedDate
    private Date createDate;
    // 更新时间
    @LastModifiedDate
    private Date updateDate;
    // 活动ID
    private Long activityId;
    // 打卡照片
    private String clockInUrl;
    // 数据状态
    private Byte status = StatusEnum.OK.getCode();

    private Integer clockStatus;

    private String nickName;

}