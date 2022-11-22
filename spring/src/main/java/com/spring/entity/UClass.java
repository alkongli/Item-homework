package com.spring.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("班级")
public class UClass {
    private static final long serialVersionUID = 1L;

    private Long id;

    //    班级名
    private String name;
}
