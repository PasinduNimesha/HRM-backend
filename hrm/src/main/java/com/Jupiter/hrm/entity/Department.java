package com.Jupiter.hrm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Department {
    @Id
    private int department_id;
    private String name;
    private String building;
    private String manager;
}
