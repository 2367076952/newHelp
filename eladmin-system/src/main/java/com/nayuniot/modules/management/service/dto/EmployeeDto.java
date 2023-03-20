package com.nayuniot.modules.management.service.dto;

import com.nayuniot.base.BaseDTO;
import com.nayuniot.modules.management.domain.District;
import com.nayuniot.modules.management.domain.Nation;
import com.nayuniot.modules.money.domain.Purpose;
import com.nayuniot.modules.system.domain.Dept;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * 人员信息类
 */

@Getter
@Setter
public class EmployeeDto extends BaseDTO implements Serializable {

    private Long id;

    //姓名
    private String name;

    //联系方式
    private String phone;

    //身份证号
    private String IdNumber;

    //性别
    private String gender;

    //年龄
    private Integer age;

    //民族
    private Nation nation;

    //家庭住址
    private Dept address;

    //详细地址
    private String detailed;
    //省
    private District provinceId;
    //市
    private District cityId;
    //户籍所在地
    private District register;

    //救助原因
    private Set<Purpose> purpose;

    //备注
    private String remarks;

    //图片
    private String image;
    //用途
    private String succourName;
    //户籍
    private String registers;

    private String change;
}
