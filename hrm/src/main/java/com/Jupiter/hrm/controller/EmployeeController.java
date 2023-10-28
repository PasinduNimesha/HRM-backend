package com.Jupiter.hrm.controller;
import com.Jupiter.hrm.entity.Employee;
import com.Jupiter.hrm.dto.EmployeeDto;
import com.Jupiter.hrm.entity.EmployeeExtension;
import com.Jupiter.hrm.service.EmployeeExtensionService;
import com.Jupiter.hrm.service.EmployeeService;
import com.Jupiter.hrm.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employees")

public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeExtensionService employeeExtensionService;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeExtensionService employeeExtensionService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.employeeExtensionService = employeeExtensionService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public HttpStatus createEmployee(@RequestBody EmployeeDto employeeDto) {
        int employee_id = employeeDto.getEmployee_id();
        Long id = employeeService.createEmployee(modelMapper.map(employeeDto, Employee.class));

        return HttpStatus.CREATED;
    }
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }



    @PostMapping("/add-attribute")
    public HttpStatus addAttribute(@RequestBody Map<String, String> attributes) {
        employeeExtensionService.addAttributes(attributes.get("attribute"), attributes.get("constraint"));
        return HttpStatus.OK;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable int id) {
        EmployeeDto employee = modelMapper.map(employeeService.getEmployeeById(id), EmployeeDto.class);
        return new ResponseEntity<>(employee, HttpStatus.OK);

    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpStatus updateEmployee(@PathVariable int id, @RequestBody EmployeeDto updatedEmployee) {
        System.out.println(updatedEmployee.getEmergency_contact_id());
        if(employeeService.updateEmployee(id, modelMapper.map(updatedEmployee, Employee.class))){
            return HttpStatus.OK;
        }
        else{
            return HttpStatus.NOT_FOUND;
        }


    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/attributes")
    public ResponseEntity<Set<String>> getAttributes(){
        Set<String> attributes = employeeExtensionService.getAttributes();
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }



}

