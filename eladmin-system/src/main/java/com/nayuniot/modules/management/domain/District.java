package com.nayuniot.modules.management.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 户籍地址表
 */
@Entity
@Getter
@Setter
@Table(name = "district")
@AllArgsConstructor
@NoArgsConstructor
public class District implements Serializable {
    @Id
    @ApiModelProperty(value = "区划信息")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "父级挂接id")
    private String pid;

    @ApiModelProperty(value = "区划编码")
    private String code;

    @ApiModelProperty(value = "区划名称")
    private String name;

    /**
     * 0:省/自治区/直辖市 1:市级 2:县级
     */
    @ApiModelProperty(value = "级次id")
    private String level;


    public District(Integer valueOf) {
    }
}
