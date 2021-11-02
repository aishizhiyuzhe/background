package com.ming.entity;


import java.util.Date;

public class User {

  private String id;
  private String loginName;
  private String password;
  private String name;
  private String email;
  private String phone;
  private String mobile;
  private String userType;
  private String photo;
  private String loginIp;
  private Date loginDate;
  private String loginFlag;
  private String createBy;
  private Date createDate;
  private String updateBy;
  private Date updateDate;
  private String remarks;
  private String delFlag;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }


  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }


  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }


  public String getLoginIp() {
    return loginIp;
  }

  public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }


  public Date getLoginDate() {
    return loginDate;
  }

  public void setLoginDate(java.sql.Timestamp loginDate) {
    this.loginDate = loginDate;
  }


  public String getLoginFlag() {
    return loginFlag;
  }

  public void setLoginFlag(String loginFlag) {
    this.loginFlag = loginFlag;
  }


  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }


  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(java.sql.Timestamp createDate) {
    this.createDate = createDate;
  }


  public String getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }


  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(java.sql.Timestamp updateDate) {
    this.updateDate = updateDate;
  }


  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }


  public String getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(String delFlag) {
    this.delFlag = delFlag;
  }

}
