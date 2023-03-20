package com.nayuniot.modules.money.service.dto;

import com.nayuniot.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class RescueTypeDto extends BaseDTO implements Serializable {

    private Long id;

    private String name;

    private String remarks;
}
