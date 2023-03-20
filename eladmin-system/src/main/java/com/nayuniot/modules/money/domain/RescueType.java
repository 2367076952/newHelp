package com.nayuniot.modules.money.domain;

import com.nayuniot.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "rescue_type")
public class RescueType extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "救助类型的名称")
    @Column(name = "rescue_name")
    private String name;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;
}
