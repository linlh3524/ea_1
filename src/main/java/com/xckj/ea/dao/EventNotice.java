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
@Table(name="event_notice")
@ApiModel(description = "通知表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventNotice {
    @ApiModelProperty(value = "编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_" )
    private int id ;
    @ApiModelProperty(value = "事件ID")
    @Column(name = "EVENT_ID_",nullable=true)
    private String eventId;
    @ApiModelProperty(value = "通知名单ID")
    @Column(name = "NOTICE_MEMBER_ID_",nullable=true)
    private String noticeMemberId;






    public EventNotice() {

    }
}
