package com.linln.modules.activity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linln.common.utils.StatusUtil;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lovec
 * @date 2019/05/08
 */
@Data
@Entity
@Table(name="t_activity")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = StatusUtil.notDelete)
public class Activity implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    // 备注
    private String remarks;
    // 创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    // 更新时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
    // 数据状态
    private Byte status;
    // 标题
    private String caption;
    // 内容
    private String content;
    // 开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date startDate;
    // 结束时间
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    // 是否发布
    private Integer isPublish;
    // 是否删除
    private Integer isDeleted;

    //照片url
    private String photoUrl;
}