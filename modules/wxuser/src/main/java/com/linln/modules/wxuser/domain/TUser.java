package com.linln.modules.wxuser.domain;

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
@Table(name="t_user")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.notDelete)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TUser implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 手机号码
    private String userPhone;
    // OpenId
    private String openId;
    // 创建时间
    @CreatedDate
    private Date createDate;
    // 更新时间
    @LastModifiedDate
    private Date updateDate;
    // 用户头像
    private String userHeadUrl;
    // 是否删除
    private Integer isDeleted;
    // 数据状态
    private Byte status = StatusEnum.OK.getCode();
    // 备注
    private String remarks;
    // 昵称
    private String nickName;
    // 性别
    private String sex;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;

}