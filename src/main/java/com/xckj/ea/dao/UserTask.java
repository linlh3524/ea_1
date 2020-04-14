package com.xckj.ea.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
@Data
@AllArgsConstructor
@Entity
@Table(name="user_task")
@ApiModel(description = "用户任务表")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserTask {

    @ApiModelProperty(value = "编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_")
    private int id ;


    @ApiModelProperty(value = "用户ID")
    @Column(name = "USER_ID_",nullable=false)
    private String userId;

    @ApiModelProperty(value = "任务主题")
    @Column(name = "THEME",nullable=true)
    private String theme;

    @ApiModelProperty(value = "任务内容")
    @Column(name = "CONTENT_",nullable=true)
    private String content;


}
