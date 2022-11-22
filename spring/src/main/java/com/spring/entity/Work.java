package com.spring.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@ApiModel("老师发布的作业")
public class Work {
    private static final long serialVersionUID = 1L;

    private Long id;

//    作业名
    @ApiModelProperty("作业名")
    private String name;

//    作业描述
    private String text;

//    创建人id
    private Long createId;

//    创建时间
    private LocalDateTime createTime;

//    截止时间
    private LocalDateTime endTime;
    //    所属班级
    private Long classId;
}
