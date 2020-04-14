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

@Table(name="act_re_procdef")
@ApiModel(description = "流程定义表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeplDefEntity {
    public DeplDefEntity( )
    {  }
    @ApiModelProperty(value = "编号")
    @Column(name = "ID_",nullable=true)
    @Id
    private String id ;

    @ApiModelProperty(hidden = true)
    @Column(name = "NAME_",nullable=true)
    private String name;
    @Column(name = "KEY_",nullable=true)
    @ApiModelProperty(value = "键")
    private String key;
    @ApiModelProperty(value = "部署Id")
   @Column(name = "DEPLOYMENT_ID_",nullable=true)
    private String deployId;

    @ApiModelProperty(value = "流程图")
    @Column(name = "DGRM_RESOURCE_NAME_",nullable=true)
    private String dgrmResourceName;



}
