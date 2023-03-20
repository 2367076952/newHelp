package com.nayuniot.modules.money.service.dto;

import com.nayuniot.base.BaseDTO;
import com.nayuniot.modules.money.domain.Material;
import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.money.domain.RescueType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
public class FundPlanningDto extends BaseDTO implements Serializable {

    //    序号
    private Long id;
    //项目名称
    private String name;
    //规划金额
    private Double planMoney;
    //规划数量
    private Integer planNumber;
    //登记的资金的id
    private String registrationId;
    //备注
    private String remarks;
    //用途
    private Set<Purpose> purpose;
    //物品
    private Material material;
    //用途名称
    private String purposeName;
    //    救助类型
    private Set<RescueType> rescueType;

    //救助类型名称
    private String rescueTypeName;


}
