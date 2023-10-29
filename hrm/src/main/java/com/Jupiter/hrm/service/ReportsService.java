package com.Jupiter.hrm.service;

import com.Jupiter.hrm.dto.DepartmentGroup;
import com.Jupiter.hrm.dto.JobTitleGroup;
import com.Jupiter.hrm.dto.PayGradeGroup;
import com.Jupiter.hrm.entity.Employee;
import com.Jupiter.hrm.repository.EmployeeRepo;
import com.Jupiter.hrm.repository.LeaveApplicationRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class ReportsService {

    private EmployeeRepo employeeRepo;
    private LeaveApplicationRepo leaveApplicationRepo;

    public ReportsService(EmployeeRepo employeeRepo, LeaveApplicationRepo leaveApplicationRepo){
        this.employeeRepo = employeeRepo;
        this.leaveApplicationRepo = leaveApplicationRepo;

    }

    public List<DepartmentGroup> getEmployeeGroupByDepartment(){
        return employeeRepo.findGroupByDepartment();
    }

    public HashMap<String, Integer> getLeavesByDepartment(String start_date, String end_date){
        return leaveApplicationRepo.getCountByDepartment(start_date, end_date);
    }

    public List<JobTitleGroup> getEmployeeGroupByJobTitle(){
        return employeeRepo.findGroupByJobTitle();
    }

    public List<PayGradeGroup> getEmployeeGroupByPayGrade() {
        return employeeRepo.findGroupByPayGrade();
    }
}
