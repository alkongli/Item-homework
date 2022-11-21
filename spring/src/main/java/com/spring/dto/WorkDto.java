package com.spring.dto;


import com.spring.entity.SWork;
import com.spring.entity.Work;
import lombok.Data;

@Data
public class WorkDto extends Work {
//  作业截止天数或作业延长天数
    private Integer days;

//  班级名
    private String className;

//    作业提交人数
    private Integer num;
}
