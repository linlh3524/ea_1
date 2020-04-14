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
@Table(name="event_type")
@ApiModel(description = "事件类型表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventType {

    @ApiModelProperty(value = "编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_" )
    private int id ;
    @ApiModelProperty(value = "事件名称")
    @Column(name = "NAME_",nullable=true)
    private String name;
    @ApiModelProperty(value = "事件等级")
    @Column(name = "EVENT_LEVEL_",nullable=true)
    private String eventLevel;

    @ApiModelProperty(value = "关联预案")
    @Column(name = "RELEVANT_DOC_",nullable=true)
    private String relevantDoc;
    @ApiModelProperty(value = "默认专家组")
    @Column(name = "DEFAULT_EXPERT_",nullable=true)
    private String defaultExperts;




    public EventType() {

    }
}
