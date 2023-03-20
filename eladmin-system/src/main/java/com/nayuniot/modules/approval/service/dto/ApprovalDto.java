package com.nayuniot.modules.approval.service.dto;

import com.nayuniot.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 人员信息类
 */

@Getter
@Setter
public class ApprovalDto extends BaseDTO implements Serializable {

    private Long id;
    //被修改人的姓名
    private String eid;
    //审批状态
    private Integer status;
    //审批内容
    private String approvalUpdate;
    //修改前内容
    private String beforeEmployee;
    //请求地址
    private String urlAddress;
    //申请原因
    private String updateCause;
    //申请方法
    private String method;
    //当前行id
    private Long rowId;
    //请求方式
    private String options;

}
