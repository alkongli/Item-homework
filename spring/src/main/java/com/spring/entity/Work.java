package com.spring.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Work {
    private static final long serialVersionUID = 1L;

    private Long id;

//    作业名
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
