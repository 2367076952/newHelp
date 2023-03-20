package com.nayuniot.modules.approval.domain;

import com.nayuniot.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
/**
 * @NamedEntityGraph 解决了sql查询过多的问题
 */
@Table(name = "employee_approval")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Approval extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "e_id")
    @ApiModelProperty(value = "人员姓名")
    private String eid;

    @ApiModelProperty(value = "审批状态")
    private Integer status;
    //审批内容
    @Column(name = "approval_employee")
    @ApiModelProperty(value = "审批内容")
    private String approvalUpdate;

    //修改前的信息
    @Column(name = "before_employee")
    @ApiModelProperty(value = "修改前的信息")
    private String beforeEmployee;


    @ApiModelProperty(value = "请求地址")
    @Column(name = "url_address")
    private String urlAddress;

    @ApiModelProperty(value = "申请原因")
    @Column(name = "update_cause")
    private String updateCause;

    @ApiModelProperty(value = "申请方法")
    private String method;

    @ApiModelProperty(value = "当前行id")
    @Column(name = "row")
    private Long rowId;

    @ApiModelProperty(value = "请求方式")
    private String options;


}





