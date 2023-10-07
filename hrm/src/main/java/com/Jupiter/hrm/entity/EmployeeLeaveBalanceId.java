package com.Jupiter.hrm.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class EmployeeLeaveBalanceId implements Serializable {

    @Column(name = "employee_id")
    private Long employee_id;
    @Column(name = "leave_type_id")
    private Long leave_type_id;

}
