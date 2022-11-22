package com.spring.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * 用户信息
 */
@Data
@ApiModel("用户信息")
public class User  {

    private static final long serialVersionUID = 1L;

    private Long id;

//    姓名
    private String name;


//    密码
    private String password;


//    性别 0 女 1 男
    @ApiModelProperty("性别 0 女 1 男")
    private String sex;


//    账号类型 0:管理员 1：教师 2：学生
    @ApiModelProperty("账号类型 0:管理员 1：教师 2：学生")
    private Integer type;

//    状态 0:禁用，1:正常
    @ApiModelProperty("状态 0:禁用，1:正常")
    private Integer status;

//    创建时间
    private LocalDateTime createTime;

//    所属班级
    private Long classId;
}
