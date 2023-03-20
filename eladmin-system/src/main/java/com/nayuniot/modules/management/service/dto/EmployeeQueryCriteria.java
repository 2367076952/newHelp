package com.nayuniot.modules.management.service.dto;

import com.nayuniot.annotation.Query;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class EmployeeQueryCriteria {
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
    //性别
    @Query(type = Query.Type.INNER_LIKE)
    private String gender;
    //年龄
    @Query(type = Query.Type.GREATER_THAN, propName = "age")
    private Integer ageMin;

    //年龄
    @Query(type = Query.Type.LESS_THAN, propName = "age")
    private Integer ageMax;

    //地址查询
    @Query(propName = "id", type = Query.Type.IN, joinName = "address")
    private Set<Long> deptIds = new HashSet<>();

    private Long deptId;

    //用途的id
    @Query(type = Query.Type.OR_LIKE, propName = "purposeList")
    private Set<Integer> purposeId;

    //创建时间
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;


}
