package com.xckj.ea.dao;

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
@Table(name="act_id_membership")
@ApiModel(description = "用户角色匹配表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Membership {

    @ApiModelProperty(value = "用户ID")
    @Id
    @Column(name = "USER_ID_",nullable=false)
    private String userId;

    @ApiModelProperty(value = "组ID")
    @Column(name = "GROUP_ID_",nullable=false)
    private String groupId;
    public Membership() {



    }
}
