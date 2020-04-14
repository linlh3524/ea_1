package com.xckj.ea.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name="yingji_event")
@ApiModel(description = "事件表")
public class yingjiEvent {
    public yingjiEvent() {    }
    @ApiModelProperty(value = "编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_" )
    private int id ;
    @ApiModelProperty(value = "流程实例ID")
    @Column(name = "PRO_DEF_ID",nullable=false)

    private String proDefId;


    @ApiModelProperty(value = "事件信息")
    @Column(name = "NAME_",nullable=false)
    private String name;

    @ApiModelProperty(value = "地点")
    @Column(name = "LOCATION_",nullable=false)
    private String lcation;

    @ApiModelProperty(value = "事件")
    @Column(name = "EVENT_THEME_",nullable=true)
    private String eventTheme;

    @ApiModelProperty(value = "死亡人数")
    @Column(name = "DEATH_COUNT_",nullable=true)
    private int deathCount;
    @ApiModelProperty(value = "事件描述")
    @Column(name = "DESCRIPTION_",nullable=true)
    private String description;

    @ApiModelProperty(value = "接收时间")
    @Column(name = "RECEIVE_TIME_",nullable=true)
    private java.util.Date receiveTime;

    @ApiModelProperty(value = "事件类型")
    @Column(name = "EVENT_TYPE_ID_",nullable=true)
    private int eventTypeId;
//    @ApiModelProperty(value = "事件等级")
//    @Column(name = "EVENT_LEVEL_",nullable=true)
//    private String eventLevel;
//
//    @ApiModelProperty(value = "关联预案")
//    @Column(name = "RELEVANT_DOC_",nullable=true)
//
//    private String relevantDoc;
//    @ApiModelProperty(value = "默认专家组信息")
//    @Column(name = "DEFAULT_EXPERT_",nullable=true)
//
//    private String defaultExpert;






}
