package com.web.entity;

/**
 * Created by gaoyang on 16/3/26.
 */

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "casemanage")
public class CaseManage {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "createname")
    private String createname;
    @Column(name = "createtime")
    private Date createtime;
    @Column(name = "updatetime")
    private Date updatetime;
    @Column(name = "phone1")
    private String phone1;
    @Column(name = "sex")
    private int sex;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "relation")
    private String relation;
    @Column(name = "city")
    private int city;
    @Column(name = "country")
    private int country;
    @Column(name = "street")
    private int street;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "phone2")
    private String phone2;
    @Column(name = "fax")
    private String fax;
    @Column(name = "phonetime")
    private String phonetime;
    @Column(name = "remark")
    private String remark;
    @Column(name = "createempid")
    private int createempid;
    @Column(name = "updateempid")
    private int updateempid;
    @Column(name = "userid")
    private int userid;
    @Column(name = "file")
    private String file;
    @Column(name = "type")
    private int type;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public String getCreatename() {
        return this.createname;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return this.sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCity() {
        return this.city;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getCountry() {
        return this.country;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public int getStreet() {
        return this.street;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return this.fax;
    }

    public void setPhonetime(String phonetime) {
        this.phonetime = phonetime;
    }

    public String getPhonetime() {
        return this.phonetime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setCreateempid(int createempid) {
        this.createempid = createempid;
    }

    public int getCreateempid() {
        return this.createempid;
    }

    public void setUpdateempid(int updateempid) {
        this.updateempid = updateempid;
    }

    public int getUpdateempid() {
        return this.updateempid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return this.userid;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return this.file;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
