package com.Jupiter.hrm.dto;

import lombok.Data;


@Data
public class UserDto {
    private long user_id;
    private String username;
    private String password;
    private String employee_id;
}
