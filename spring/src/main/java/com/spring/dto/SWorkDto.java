package com.spring.dto;

import com.spring.entity.SWork;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SWorkDto extends SWork {
    private String userName;
}
