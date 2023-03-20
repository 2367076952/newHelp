package com.nayuniot.modules.money.domain;

import com.nayuniot.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Table(name = "fund_registration")
@Entity
@NamedEntityGraph(name = "fundRegistration", attributeNodes = {@NamedAttributeNode("purpose"), @NamedAttributeNode("source"), @NamedAttributeNode("fundPlanning")})
/**
 * @DynamicInsert属性：设置为true，表示insert对象的时候，生成动态的insert语句，如果这个字段的值是null就不会加入到insert语句中，默认false。
 * 比如希望数据库插入日期或时间戳字段时，在对象字段为空定的情况下，表字段能自动填写当前的sysdate。
 * @DynamicUpdate属性：设置为true，表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中，默认false。
 */
@DynamicInsert
@DynamicUpdate
public class FundRegistration extends BaseEntity implements Serializable {
    @Id
    @Column(name = "fund_registration_id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "来源")
    @JoinColumn(name = "source_id")
    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE) //  这样，当子表中没找到数据时，主表中对应的field就是null，而不会报错了。
    private Source source;


    @ApiModelProperty(value = "用途")
    @ManyToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinTable(name = "money_fund_purpose",
            joinColumns = {@JoinColumn(name = "fund_id", referencedColumnName = "fund_registration_id")},
            inverseJoinColumns = {@JoinColumn(name = "purpose_id", referencedColumnName = "id")})
    @OrderBy
    private Set<Purpose> purpose;

    @Transient
    @ApiModelProperty(value = "来源名称")
    private String sourceName;

    @Transient
    @ApiModelProperty(value = "用途名称")
    private String purposeName;


    @ApiModelProperty(value = "接收人")
    private String recipient;

    @Column(name = "receiving_unit")
    @ApiModelProperty(value = "接收单位")
    private String receivingUnit;

    @Column(name = "receipt_time")
    @ApiModelProperty(value = "到账时间")
    private Timestamp receiptTime;

    @Column(name = "remarks")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "剩余资金")
    @Transient
    private Double remainingFunds;

    @ApiModelProperty("资金规划")
    @OneToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "registration_id", referencedColumnName = "fund_registration_id")
    private Set<FundPlanning> fundPlanning;

    @ApiModelProperty(value = "用途列表(方便进行in查询)")
    @Column(name = "purpose_list")
    private String purposeList;

    @ApiModelProperty("状态")
    @Column(name = "status")
    private int status = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getSourceName() {
        return getSource() == null ? "" : getSource().getName();
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getPurposeName() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Purpose purpose1 : getPurpose()) {
            stringBuilder.append(purpose1.getName()).append(",");
        }
        return stringBuilder.toString();
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public Set<Purpose> getPurpose() {
        return purpose;
    }

    public void setPurpose(Set<Purpose> purpose) {
        this.purpose = purpose;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getReceivingUnit() {
        return receivingUnit;
    }

    public void setReceivingUnit(String receivingUnit) {
        this.receivingUnit = receivingUnit;
    }

    public Timestamp getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Timestamp receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getRemainingFunds() {
        Double v = 0.00;
        for (FundPlanning planning : getFundPlanning()) {
            v += planning.getPlanNumber() * planning.getPlanMoney();
        }
        return getMoney() - v;
    }

    public void setRemainingFunds(Double remainingFunds) {
        this.remainingFunds = remainingFunds;
    }

    public String getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(String purposeList) {
        this.purposeList = purposeList;
    }

    public Set<FundPlanning> getFundPlanning() {
        return fundPlanning;
    }

    public void setFundPlanning(Set<FundPlanning> fundPlanning) {
        this.fundPlanning = fundPlanning;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
