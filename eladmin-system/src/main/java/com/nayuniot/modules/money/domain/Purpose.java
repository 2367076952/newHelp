package com.nayuniot.modules.money.domain;

import com.nayuniot.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "money_purpose")
@Getter
@Setter
public class Purpose extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Transient
    private Long id;

    @ApiModelProperty(value = "用途名称")
    @Column(name = "name")
    private String name;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;
}
