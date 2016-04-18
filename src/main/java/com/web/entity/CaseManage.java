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
    @Column(name = "apply_name")
    private String apply_name;
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
    private String city;

    @Column(name = "doctor_name")
    private String doctor_name;

    @Column(name = "doctor_hospital")
    private String doctor_hospital;

    @Column(name = "doctor_major")
    private String doctor_major;

    @Column(name = "province")
    private String province;
    @Column(name = "country")
    private String country;
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
    @Column(name = "status")
    private int status;
    @Column(name = "note")
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getStreet() {
        return street;
    }

    public void setStreet(int street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhonetime() {
        return phonetime;
    }

    public void setPhonetime(String phonetime) {
        this.phonetime = phonetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreateempid() {
        return createempid;
    }

    public void setCreateempid(int createempid) {
        this.createempid = createempid;
    }

    public int getUpdateempid() {
        return updateempid;
    }

    public void setUpdateempid(int updateempid) {
        this.updateempid = updateempid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApply_name() {
        return apply_name;
    }

    public void setApply_name(String apply_name) {
        this.apply_name = apply_name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_hospital() {
        return doctor_hospital;
    }

    public void setDoctor_hospital(String doctor_hospital) {
        this.doctor_hospital = doctor_hospital;
    }

    public String getDoctor_major() {
        return doctor_major;
    }

    public void setDoctor_major(String doctor_major) {
        this.doctor_major = doctor_major;
    }
}

