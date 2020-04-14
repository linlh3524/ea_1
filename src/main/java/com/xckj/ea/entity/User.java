package com.xckj.ea.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor

public class User {
    public User(String id, String pwd, Integer rev, String firstName, String lastName, String email )
    {
        this.email=email;
        this.firstName=firstName;
        this.id=id;
        this.lastName=lastName;
        this.rev=rev;

    }
    @ApiModelProperty(value = "编号")

    private String id ;

    @ApiModelProperty(hidden = true)
   // @Column(name = "REV_",nullable=true)
    private Integer rev;

    @ApiModelProperty(value = "姓氏")
   // @Column(name = "FIRST_",nullable=true)
    private String firstName;

    @ApiModelProperty(value = "名字")
   // @Column(name = "LAST_",nullable=true)
    private String lastName;

    @ApiModelProperty(value = "邮箱")
   // @Column(name = "EMAIL_",nullable=true)
    private String email;

    @ApiModelProperty(value = "密码")
  //  @Column(name = "PWD_",nullable=true)
    private String pwd;

    @ApiModelProperty(value = "照片ID")
   // @Column(name = "PICTURE_ID_",nullable=true)
    private String picId;

    private  String salt;

    public User() {

    }

    public String getCrentialSalt(){
        return id+pwd+salt;
    }







}
