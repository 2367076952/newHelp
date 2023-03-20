package com.nayuniot.modules.management.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
/**
 * @NamedEntityGraph 解决了sql查询过多的问题
 */
@Table(name = "employee_purpose")
public class EmployeePurpose implements Serializable {
    @Id
    @Column(name = "employee_id")
    @ApiModelProperty(value = "ID")
    private Long employeeId;

    @Id
    @Column(name = "purpose_id")
    @ApiModelProperty(value = "ID")
    private Long purposeId;
}
