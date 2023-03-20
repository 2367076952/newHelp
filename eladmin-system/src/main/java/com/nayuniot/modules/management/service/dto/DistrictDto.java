package com.nayuniot.modules.management.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DistrictDto implements Serializable {
    //区划信息
    private Long id;
    //父级
    private String pid;
    //区划编码
    private String code;
    //区划名称
    private String name;
    //级次
    private String level;
}
