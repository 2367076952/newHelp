package com.nayuniot.modules.money.domain;

import com.nayuniot.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "fund_planning")
@NamedEntityGraph(name = "FundPlanning",
        attributeNodes = {
                @NamedAttributeNode("purpose"),
                @NamedAttributeNode("material"),
                @NamedAttributeNode("rescueType")
        })
public class FundPlanning extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "项目名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "金额")
    private Double planMoney;

    @ApiModelProperty(value = "数量")
    private Integer planNumber;

    @ApiModelProperty(value = "登记资金Id")
    @Column(name = "registration_id")
    private Long registrationId;

    @ApiModelProperty(value = "用途")
    @ManyToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy
    @JoinTable(name = "money_planning_purpose",
            joinColumns = {@JoinColumn(name = "planning_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "purpose_id", referencedColumnName = "id")})
    private Set<Purpose> purpose;

    @ApiModelProperty(value = "用途名称")
    @Transient
    private String purposeName;

    @ApiModelProperty(value = "用途列表(方便进行in查询)")
    @Column(name = "purpose_list")
    private String purposeList;

    @ApiModelProperty(value = "物品")
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Material material;

    @ApiModelProperty(value = "救助类型")
    @ManyToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy
    @JoinTable(name = "planning_rescue_type",
            joinColumns = {@JoinColumn(name = "planning_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rescue_type_id", referencedColumnName = "id")})
    private Set<RescueType> rescueType;

    @ApiModelProperty(value = "index页面展示的救助类型")
    @Transient
    private String rescueTypeName;

    @ApiModelProperty(value = "剩余资金（接收前端传过来的参数不做展示）")
    @Transient
    private double remainingFunds;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPlanMoney() {
        return planMoney;
    }

    public void setPlanMoney(Double planMoney) {
        this.planMoney = planMoney;
    }

    public Integer getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(Integer planNumber) {
        this.planNumber = planNumber;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public Set<Purpose> getPurpose() {
        return purpose;
    }

    public void setPurpose(Set<Purpose> purpose) {
        this.purpose = purpose;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPurposeName() {
        StringBuilder purposeNameBuilder = new StringBuilder();
        for (Purpose purpose1 : getPurpose()) {
            purposeNameBuilder.append(purpose1.getName()).append(",");
        }
        purposeName = purposeNameBuilder.toString();
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRemainingFunds() {
        return remainingFunds;
    }

    public void setRemainingFunds(double remainingFunds) {
        this.remainingFunds = remainingFunds;
    }

    public Set<RescueType> getRescueType() {
        return rescueType;
    }

    public void setRescueType(Set<RescueType> rescueType) {
        this.rescueType = rescueType;
    }

    public String getRescueTypeName() {
        StringBuilder purposeNameBuilder = new StringBuilder();
        for (RescueType rescueType : getRescueType()) {
            purposeNameBuilder.append(rescueType.getName()).append(",");
        }
        rescueTypeName = purposeNameBuilder.toString();
        return rescueTypeName;
    }

    public void setRescueTypeName(String rescueTypeName) {
        this.rescueTypeName = rescueTypeName;
    }

    public String getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(String purposeList) {
        this.purposeList = purposeList;
    }

}
