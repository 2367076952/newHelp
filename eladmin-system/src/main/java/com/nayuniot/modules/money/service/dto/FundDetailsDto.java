package com.nayuniot.modules.money.service.dto;

import com.nayuniot.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 资金规划里传到前端的实体dto
 */
@Getter
@Setter
public class FundDetailsDto extends BaseDTO implements Serializable {
    //序号
    private Long id;
    //金额
    private Double money;
    //剩余金额
    private Double remainingFunds;
    //来源
    private String sourceName;
    //用途
    private String purposeName;
    //到账时间
    private Timestamp receiptTime;
    //接收人
    private String recipient;
    //接收单位
    private String receivingUnit;


}
