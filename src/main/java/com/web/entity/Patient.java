package com.web.entity;

/**
 * Created by gaoyang on 16/3/6.
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
@Table(name = "patient")
public class Patient{
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="name")
    private String name;
    @Column(name ="username")
    private String username;
    @Column(name ="pwd")
    private String pwd;
    @Column(name ="createtime")
    private Date createtime;
    @Column(name ="updatetime")
    private Date updatetime;
    @Column(name ="phone1")
    private String phone1;
    @Column(name ="sex")
    private int sex;
    @Column(name ="address")
    private String address;
    @Column(name ="email")
    private String email;

    @Column(name ="phone2")
    private String phone2;
    @Column(name ="province")
    private int province;
    @Column(name ="city")
    private int city;
    @Column(name ="fax")
    private String fax;
    @Column(name ="zipcode")
    private String zipcode;

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return this.username;
    }

    public void setPwd(String pwd){
        this.pwd=pwd;
    }
    public String getPwd(){
        return this.pwd;
    }

    public void setCreatetime(Date createtime){
        this.createtime=createtime;
    }
    public Date getCreatetime(){
        return this.createtime;
    }

    public void setUpdatetime(Date updatetime){
        this.updatetime=updatetime;
    }
    public Date getUpdatetime(){
        return this.updatetime;
    }

    public void setSex(int sex){
        this.sex=sex;
    }
    public int getSex(){
        return this.sex;
    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return this.address;
    }

    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }

}