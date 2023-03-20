package com.nayuniot.modules.management.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 民族信息
 */
@Setter
@Getter
public class NationDto implements Serializable {

    private Long id;

    //民族
    private String name;
}