package com.nayuniot.modules.money.domain;

import com.nayuniot.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="money_source")
@Getter
@Setter
@Entity
public class Source extends BaseEntity implements Serializable {
    @Id
    @Column(name = "source_id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "来源名称")
    @Column(name = "name")
    private String name;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;
}
