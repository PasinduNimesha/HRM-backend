package com.Jupiter.hrm.controller;


import com.Jupiter.hrm.dto.Dates;
import com.Jupiter.hrm.dto.DepartmentGroup;
import com.Jupiter.hrm.dto.JobTitleGroup;
import com.Jupiter.hrm.dto.PayGradeGroup;
import com.Jupiter.hrm.entity.Employee;
import com.Jupiter.hrm.service.EmployeeService;
import com.Jupiter.hrm.service.ReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    private final EmployeeService employeeService;
    private final ReportsService reportsService;
    public ReportsController(EmployeeService employeeService, ReportsService reportsService){
        this.employeeService = employeeService;
        this.reportsService = reportsService;

    }


//    Total leaves in given period by department

    @GetMapping("/leavesbydepartment")
    public ResponseEntity<HashMap<String, Integer>> getLeavesByDepartment(){
        Dates dates = new Dates();
        dates.setStart_date("2021-01-01");
        dates.setEnd_date("2025-12-31");
        System.out.println("start date: "+ dates.getStart_date());
        System.out.println("end date: "+dates.getEnd_date());

        HashMap<String, Integer> leaves = reportsService.getLeavesByDepartment(dates.getStart_date(), dates.getEnd_date());
        if (leaves != null){
            return ResponseEntity.ok(leaves);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    //    Employee reports grouped by job title, department, pay grade etc.
    @GetMapping("/employeebyjobtitle")
    public ResponseEntity<List<JobTitleGroup>> getEmployeeByJobTitle(){
        List<JobTitleGroup> employees = reportsService.getEmployeeGroupByJobTitle();
        if (employees != null){
            return ResponseEntity.ok(employees);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/employeebydepartment")
    public ResponseEntity<List<DepartmentGroup>> getEmployeeByDepartment(){
        List<DepartmentGroup> employees = reportsService.getEmployeeGroupByDepartment();
        if (employees != null){
            return ResponseEntity.ok(employees);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/employeebypaygrade")
    public ResponseEntity<List<PayGradeGroup>> getEmployeeByPayGrade(){
        List<PayGradeGroup> employees = reportsService.getEmployeeGroupByPayGrade();
        if (employees != null){
            return ResponseEntity.ok(employees);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


}
