package com.nayuniot.modules.line.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PurposePeopleDto implements Serializable {
    private String name;
    private String purposeName;
    private String count;
    private String time;
}
