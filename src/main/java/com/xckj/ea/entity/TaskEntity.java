package com.xckj.ea.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity

@ApiModel(description = "任务表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskEntity {
    public TaskEntity( )
    {


    }
    @ApiModelProperty(value = "编号")
    @Id
    @Column(name = "ID_",nullable = false,unique = true)
    private String id ;

    @ApiModelProperty(hidden = true)
    @Column(name = "NAME_",nullable=true)
    private String name;

    @ApiModelProperty(value = "办理人")
    @Column(name = "ASSIGNEE_",nullable=true)
    private String assignee;
    @ApiModelProperty(value = "开始时间")
    @Column(name = "CREATE_TIME_",nullable=true)
    private String createTime;

    @ApiModelProperty(value = "开始时间")
    private String status;




}
