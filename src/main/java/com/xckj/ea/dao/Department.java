package com.xckj.ea.dao;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name="department")
@Api(tags = "部门表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department {
    @ApiModelProperty(value = "编号")
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_",nullable = false,unique = true)
    private String id ;

    @ApiModelProperty(value = "编号")
    @Column(name = "NAME_",nullable=false)
    private String name;
    @ApiModelProperty(value = "备注")
    @Column(name = "NOTE_",nullable=true)
    private String note;


}
