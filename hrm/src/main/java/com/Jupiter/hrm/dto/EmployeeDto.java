package com.Jupiter.hrm.dto;

import lombok.Data;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Data
public class EmployeeDto {
    @Id
    private int employee_id;
    private String name;
    private String birthdate;
    private String marital_status;
    private int emergency_contact_id;
    private int job_id;
    private String gender;
    private String address;
    private int employment_status_id;
    private int supervisor;
    private int branch_id;
    private Map<String, Integer> int_attributes = new HashMap<>();
    private Map<String, String> str_attributes = new HashMap<>();
    private Map<String, Double> double_attributes = new HashMap<>();

}
