package com.nayuniot.modules.money.service.dto;

import com.nayuniot.base.BaseDTO;
import com.nayuniot.modules.management.domain.Employee;
import com.nayuniot.modules.money.domain.FundPlanning;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class FundDistributionDto extends BaseDTO implements Serializable {

    private Long id;

    private Employee employee;

    private FundPlanning fundPlanning;

    private Integer number;

    private String remarks;

}
