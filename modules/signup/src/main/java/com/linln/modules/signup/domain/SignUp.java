package com.linln.modules.signup.domain;

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
 * @date 2019/05/08
 */
@Data
@Entity
@Table(name="t_sign_up")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.notDelete)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUp implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // OpenId
    private String openId;
    // 活动ID
    private Long activityId;
    // 用户ID
    private Long userId;
    // 创建时间
    @CreatedDate
    private Date createDate;
    // 更新时间
    @LastModifiedDate
    private Date updateDate;
    // 数据状态
    private Byte status = StatusEnum.OK.getCode();
    // 是否删除
    private Integer isDeleted;
    // 备注
    private String remarks;
}