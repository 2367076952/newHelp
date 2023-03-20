package com.nayuniot.modules.management.domain;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "employee_nation")
public class Nation implements Serializable {
    @Id
//    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "nation")
    @ApiModelProperty(value = "民族")
    private String nation;
}