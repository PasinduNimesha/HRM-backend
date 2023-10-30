package com.Jupiter.hrm.controller;
import com.Jupiter.hrm.dto.LeaveApplicationDto;
import com.Jupiter.hrm.entity.LeaveApplication;
import com.Jupiter.hrm.service.LeaveApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/leaveapplication")
public class LeaveApplicationController {
    private final LeaveApplicationService leaveApplicationService;
    private  final ModelMapper modelMapper;

    @Autowired
    public LeaveApplicationController(LeaveApplicationService leaveApplicationService, ModelMapper modelMapper){
        this.leaveApplicationService = leaveApplicationService;
        this.modelMapper = modelMapper;
    }



//    @PutMapping("/{id}")
//    public HttpStatus updateApplication(@RequestBody LeaveApplicationDto updatedLeaveApplication) {
//        if(leaveApplicationService.updateApplication(modelMapper.map(updatedLeaveApplication, LeaveApplication.class))){
//            return HttpStatus.OK;
//        }
//        else{
//            return HttpStatus.NOT_FOUND;
//        }
//    }

    @PutMapping("/{id}")
    public HttpStatus updateApplication(@RequestBody LeaveApplicationDto updatedapplication ,@PathVariable long id) {
        String status = updatedapplication.getStatus();
        LeaveApplication leaveApplication = leaveApplicationService.getApplication(id);
        if(leaveApplication != null){
            leaveApplication.setStatus(status);
            if(leaveApplicationService.updateApplication(leaveApplication)){
                return HttpStatus.OK;
            }
            else{
                return HttpStatus.NOT_FOUND;
            }
        }
        else{
            return HttpStatus.NOT_FOUND;
        }

    }

    @PostMapping
    public HttpStatus newApplication(@RequestBody LeaveApplicationDto updatedLeaveApplication) {
        System.out.println("employee id: "+updatedLeaveApplication.getEmployee_id());
        System.out.println("leave type id: "+updatedLeaveApplication.getLeave_type_id());
        System.out.println("start date: "+updatedLeaveApplication.getStart_date());
        System.out.println("end date: "+updatedLeaveApplication.getEnd_date());git
        System.out.println("status: "+updatedLeaveApplication.getStatus());
        if(leaveApplicationService.newApplication(modelMapper.map(updatedLeaveApplication, LeaveApplication.class))){
            return HttpStatus.OK;
        }
        else{
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/{id}")
    public List<LeaveApplication> getApplicationToUpdate(@PathVariable int id) {
        List<LeaveApplication> applications= leaveApplicationService.getApplicationsToUpdate(id);
        if(applications != null){
            return applications;
        }
        else{
            return null;
        }
    }
    @DeleteMapping("/{id}")
    public HttpStatus deleteApplication(@PathVariable Long id) {
        if(leaveApplicationService.deleteApplication(id)){
            return HttpStatus.OK;
        }
        else{
            return HttpStatus.NOT_FOUND;
        }
    }
}
