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
    @Column(name ="phone")
    private String phone;
    @Column(name ="sex")
    private int sex;
    @Column(name ="address")
    private String address;
    @Column(name ="email")
    private String email;
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

    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return this.phone;
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