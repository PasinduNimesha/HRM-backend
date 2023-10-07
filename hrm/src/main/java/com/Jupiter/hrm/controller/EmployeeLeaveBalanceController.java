package com.Jupiter.hrm.controller;
import com.Jupiter.hrm.dto.EmployeeLeaveBalanceDto;
import com.Jupiter.hrm.entity.EmployeeLeaveBalance;
import com.Jupiter.hrm.service.EmployeeLeaveBalanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/leavebalance")
public class EmployeeLeaveBalanceController {
    private final EmployeeLeaveBalanceService employeeLeaveBalanceService;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeLeaveBalanceController(EmployeeLeaveBalanceService employeeLeaveBalanceService, ModelMapper modelMapper){
        this.employeeLeaveBalanceService = employeeLeaveBalanceService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/update-balance")
    public HttpStatus updateName(@RequestBody EmployeeLeaveBalanceDto updatedEmployeeLeaveBalance) {
        if(employeeLeaveBalanceService.updateBalance(updatedEmployeeLeaveBalance)){
            return HttpStatus.OK;
        }
        else{
            return HttpStatus.NOT_FOUND;
        }
    }

}
