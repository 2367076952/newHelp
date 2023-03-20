package com.nayuniot.modules.money.service.dto;

import com.nayuniot.annotation.Query;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class FundDistributionQueryCriteria{

    @Query(type = Query.Type.EQUAL,joinName = "fundPlanning",propName = "id")
    private Integer fundPlanning;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query(type = Query.Type.RIGHT_LIKE,joinName = "employee",propName = "name")
    private String employeeName;

    @Query(type = Query.Type.INNER_LIKE,joinName = "fundPlanning",propName = "name")
    private String fundPlanningName;
}
