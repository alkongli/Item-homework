package com.spring.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SWork {
    private static final long serialVersionUID = 1L;

    private Long id;

    //    提交作业名
    private String name;

    //    作业id
    private Long workId;

    //    提交人学号
    private Long userId;

//  文件上传位置
    private String path;

    //    创建时间
    private LocalDateTime createTime;


}
