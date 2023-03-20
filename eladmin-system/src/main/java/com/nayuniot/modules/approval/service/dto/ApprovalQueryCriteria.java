package com.nayuniot.modules.approval.service.dto;

import com.nayuniot.annotation.Query;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class  ApprovalQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    //被修改人的姓名
    private String eid;
    @Query(type = Query.Type.INNER_LIKE)
    //被修改人的姓名
    private String createBy;
    @Query(type = Query.Type.INNER_LIKE)
    private Integer status;

    //创建时间
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;


}
