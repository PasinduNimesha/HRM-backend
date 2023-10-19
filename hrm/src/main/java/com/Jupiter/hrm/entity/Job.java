package com.Jupiter.hrm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Job {
    @Id
    private int job_id;
    private String job_title;
    private String pay_grade_id;
    private String department_id;
}
