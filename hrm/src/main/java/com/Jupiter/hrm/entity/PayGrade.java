package com.Jupiter.hrm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class PayGrade {
    @Id
    private int pay_grade_id;
    private String level;
    private int additional_leaves;
}
