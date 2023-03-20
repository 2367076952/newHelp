package com.nayuniot.modules.money.service.dto;

import com.nayuniot.annotation.Query;
import com.nayuniot.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FundPlanningQueryCriteria extends BaseDTO implements Serializable {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query(type = Query.Type.OR_LIKE)
    private String purposeList;

}
