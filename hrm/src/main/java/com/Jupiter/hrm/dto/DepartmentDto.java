package com.Jupiter.hrm.dto;

import lombok.Data;

@Data
public class DepartmentDto {
    private int department_id;
    private String name;
    private String building;
    private String manager;
}
