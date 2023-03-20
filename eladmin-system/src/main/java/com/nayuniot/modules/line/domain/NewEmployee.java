package com.nayuniot.modules.line.domain;

import lombok.Data;

import java.util.List;

@Data
public class NewEmployee {
    //新增数据
    private List<Integer> expectedData;
    //总数据
    private List<Integer> actualData;
}
