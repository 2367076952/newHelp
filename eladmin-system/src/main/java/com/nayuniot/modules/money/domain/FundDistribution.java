package com.nayuniot.modules.money.domain;

import com.nayuniot.base.BaseEntity;
import com.nayuniot.modules.management.domain.Employee;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "fund_distribution")
@ApiOperation("资金发放")
@NamedEntityGraph(name = "FundDistribution",
        attributeNodes = {
                @NamedAttributeNode(value = "fundPlanning", subgraph = "fundPlanning"),
                @NamedAttributeNode(value = "employee",//要懒加载employee属性中的子元素
                        subgraph = "employee"),
        },
        subgraphs = {
//        子元素列表
                @NamedSubgraph(name = "employee", attributeNodes = {
                        @NamedAttributeNode("nation"),
                        @NamedAttributeNode("address"),
                        @NamedAttributeNode("register"),
                        @NamedAttributeNode("purpose"),
                        @NamedAttributeNode("cityId"),
                        @NamedAttributeNode("provinceId")
                }),
                @NamedSubgraph(name = "fundPlanning", attributeNodes = {
                        @NamedAttributeNode("purpose"),
                        @NamedAttributeNode("material"),
                        @NamedAttributeNode("rescueType")
                })
        })
@Getter
@Setter
public class FundDistribution extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "人员信息表")
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Transient
    private List<Employee> employees;

    @ApiModelProperty(value = "资金规划表")
    @OneToOne(fetch = FetchType.LAZY)
    @OrderBy
    @JoinColumn(name = "fund_planning_id", referencedColumnName = "id")
    private FundPlanning fundPlanning;

    @ApiModelProperty("发放的数量")
    @Column(name = "number")
    private Integer number;


    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

}
