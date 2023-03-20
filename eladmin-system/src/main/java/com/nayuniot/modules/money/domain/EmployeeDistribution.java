package com.nayuniot.modules.money.domain;

import com.nayuniot.modules.management.domain.District;
import com.nayuniot.modules.management.domain.Nation;
import com.nayuniot.modules.system.domain.Dept;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
/**
 * @NamedEntityGraph 解决了sql查询过多的问题
 * 人员发放资金的视图
 */
@Table(name = "employee_distribution")
@NamedEntityGraph(name = "employeeDistribution", attributeNodes = {
        @NamedAttributeNode("nation"),
        @NamedAttributeNode("address"),
        @NamedAttributeNode("register"),
        @NamedAttributeNode("purpose"),
        @NamedAttributeNode("cityId"),
        @NamedAttributeNode("provinceId"),

})
public class EmployeeDistribution implements Serializable {


    @Id
    @Column(name = "employee_id")
    @ApiModelProperty(value = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    @Column(name = "id_number", unique = true)
    private String idNumber;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "详细地址")
    @Column(name = "detailed_address")
    private String detailed;

    @ApiModelProperty(value = "备注")
    private String remarks;


    @ApiModelProperty(value = "民族")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nation", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Nation nation;

    @ApiModelProperty(value = "家庭住址")
    @OneToOne
    @JoinColumn(name = "address", referencedColumnName = "dept_id")
    private Dept address;


    @ApiModelProperty(value = "救助原因")
    @JoinTable(name = "employee_purpose", //中间表名字
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id", referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy
    private Set<Purpose> purpose;

    @OneToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(value = "省")
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private District provinceId;

    @OneToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(value = "市")
    @NotFound(action = NotFoundAction.IGNORE)
//   referencedColumnName 引用表对应的字段，如果不注明，默认就是引用表的主键
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private District cityId;

    @ApiModelProperty(value = "户籍所在地")
    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "register", referencedColumnName = "id")
    private District register;

    @ApiModelProperty(value = "需要发放的物资 物品*数量*单价")
    private String distribution;


    @ApiModelProperty(value = "用途")
    @Transient
    private String succourName;

    @ApiModelProperty(value = "户籍")
    @Transient
    private String registers;


    public String getRegisters() {
        registers = provinceId.getName() + cityId.getName() + register.getName();
        return registers;
    }

    public void setRegisters(String registers) {
        this.registers = registers;
    }

    public String getSuccourName() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Purpose succourName : getPurpose()) {
            stringBuilder.append(succourName.getName() + " ");
        }
        succourName = stringBuilder.toString();
        return succourName;
    }

    public void setSuccourName(String succourName) {
        this.succourName = succourName;
    }

    /**
     * 获取
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    /**
     * 获取
     *
     * @return idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置
     *
     * @param idNumber
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * 获取
     *
     * @return gender
     */
    public String getGender() {

        if (gender.equals("0")) {
            this.gender = "男";
            return gender;
        } else if (gender.equals("1")) {
            this.gender = "女";
            return gender;
        }
        return gender;
    }

    /**
     * 设置
     *
     * @param gender
     */
    public void setGender(String gender) {
        if (gender.equals("男")) {
            this.gender = "0";
            return;
        } else if (gender.equals("女")) {
            this.gender = "1";
            return;
        }
    }

    /**
     * 获取
     *
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置
     *
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取
     *
     * @return nation
     */
    public Nation getNation() {
        return nation;
    }

    /**
     * 设置
     *
     * @param nation
     */
    public void setNation(Nation nation) {
        this.nation = nation;
    }

    /**
     * 获取
     *
     * @return address
     */
    public Dept getAddress() {
        return address;
    }

    /**
     * 设置
     *
     * @param address
     */
    public void setAddress(Dept address) {
        this.address = address;
    }

    /**
     * 获取
     *
     * @return register
     */

    public District getRegister() {

        return register;
    }

    /**
     * 设置
     *
     * @param register
     */
    public void setRegister(District register) {
        this.register = register;
    }

    /**
     * 获取
     *
     * @return succour
     */
    public Set<Purpose> getPurpose() {
        return purpose;
    }

    /**
     * 设置
     *
     * @param
     */
    public void setPurpose(Set<Purpose> purpose) {
        this.purpose = purpose;
    }

    /**
     * 获取
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置
     *
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public District getCityId() {
        return cityId;
    }

    public void setCityId(District cityId) {
        this.cityId = cityId;
    }

    public District getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(District provinceId) {
        this.provinceId = provinceId;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }
}
