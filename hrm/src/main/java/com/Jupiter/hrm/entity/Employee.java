package com.Jupiter.hrm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employee_id;
    private String name;
    private String birthdate;
    private String marital_status;
    private int emergency_contact_id;

    //new attributes
    private int job_id;
    private String gender;
    private String address;
    private int employment_status_id;
    private int supervisor;
    private int branch_id;

}
