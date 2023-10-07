package com.Jupiter.hrm.service;
import com.Jupiter.hrm.dto.EmployeeLeaveBalanceDto;
import com.Jupiter.hrm.entity.Employee;
import com.Jupiter.hrm.entity.EmployeeLeaveBalance;
import com.Jupiter.hrm.repository.EmployeeLeaveBalanceRepository;
import com.Jupiter.hrm.repository.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class EmployeeLeaveBalanceService {

    private EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;

    @Autowired
    public EmployeeLeaveBalanceService(EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository){
        this.employeeLeaveBalanceRepository = employeeLeaveBalanceRepository;
    }


    public boolean updateBalance(EmployeeLeaveBalanceDto employeeLeaveBalance){

        if(employeeLeaveBalanceRepository.setBalance(
                employeeLeaveBalance.getBalance(),
                employeeLeaveBalance.getEmployee_id(),
                employeeLeaveBalance.getLeave_type_id())){
            return true;
        }
        else{
            return false;
        }
    }
    public void addAttributes(String attribute, String constraint){

    }

}