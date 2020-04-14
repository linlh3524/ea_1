package com.xckj.ea.dao;



import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name="user_message")
@ApiModel(description = "用户消息表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMessage
{
    public UserMessage() {

    }
    @ApiModelProperty(value = "编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_" )
    private int id ;


    @ApiModelProperty(value = "用户ID")
    @Column(name = "USER_ID_",nullable=false)
    private String userId;
    @ApiModelProperty(value = "任务ID")
    @Column(name = "TASK_ID_",nullable=false)
    private String taskId;

    @ApiModelProperty(value = "消息主题")
    @Column(name = "THEME",nullable=true)
    private String theme;

    @ApiModelProperty(value = "消息内容")
    @Column(name = "CONTENT_",nullable=true)
    private String content;


    @ApiModelProperty(value = "发送时间")
    @Column(name = "SEND_TIME_",nullable=true)
 // @Temporal(TemporalType.TIME)
    private java.util.Date sendTime;
    @ApiModelProperty(value = "接收时间")
    @Column(name = "RECEIVE_TIME_",nullable=true)
  // @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date receiveTime;












}
