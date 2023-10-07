package com.Jupiter.hrm.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class EmployeeLeaveBalance {

    @EmbeddedId
    private EmployeeLeaveBalanceId id;
    private int balance;

}

