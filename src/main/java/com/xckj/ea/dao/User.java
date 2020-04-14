package com.xckj.ea.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@Table(name="act_id_user")
@ApiModel(description = "用户表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    public User(String id,String pwd,Integer rev,String firstName,String lastName,String email )
    {
        this.email=email;
        this.firstName=firstName;
        this.id=id;
        this.lastName=lastName;
        this.rev=rev;

    }
    @ApiModelProperty(value = "编号")
    @Id
    @Column(name = "ID_",nullable = false,unique = true)
    private String id ;

    @ApiModelProperty(hidden = true)
    @Column(name = "REV_",nullable=true)
    private Integer rev;

    @ApiModelProperty(value = "姓氏")
    @Column(name = "FIRST_",nullable=true)
    private String firstName;

    @ApiModelProperty(value = "名字")
    @Column(name = "LAST_",nullable=true)
    private String lastName;

    @ApiModelProperty(value = "姓氏")
    @Column(name = "USER_NAME_",nullable=true)
    private String userName;


    @ApiModelProperty(value = "邮箱")
    @Column(name = "EMAIL_",nullable=true)
    private String email;
//    @ApiModelProperty(value = "手机号码")
//    @Column(name = "PHONE_",nullable=true,columnDefinition="varchar[11] default '11111111111'")
//    private String phone="11111111111";

    @ApiModelProperty(value = "密码")
    @Column(name = "PWD_",nullable=true)
    private String pwd;

    @ApiModelProperty(value = "照片ID")
    @Column(name = "PICTURE_ID_",nullable=true)
    private String picId;

    @ApiModelProperty(value = "角色ID")
    @Column(name = "GROUP_ID_",nullable=true)
    private String group_id;

    private  String salt;

    public User() {

    }

    public String getCrentialSalt(){
        return id+pwd+salt;
    }







}
