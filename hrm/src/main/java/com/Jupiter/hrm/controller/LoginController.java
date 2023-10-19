package com.Jupiter.hrm.controller;
import com.Jupiter.hrm.entity.Employee;
import com.Jupiter.hrm.dto.UserDto;
import com.Jupiter.hrm.entity.User;
import com.Jupiter.hrm.service.UserService;
import com.Jupiter.hrm.service.EmployeeService;
import com.Jupiter.hrm.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final EmployeeService employeeService;

    @Autowired
    public LoginController(UserService userService, JwtUtil jwtUtil, EmployeeService employeeService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        User user = userService.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());

        if (user != null) {
            Employee employee = employeeService.getEmployeeById(user.getEmployee_id());
            String token = jwtUtil.generateToken(user);
            String role = user.getRole();

            Map<String, Object> response = new HashMap<>();
            response.put("employee", employee);
            response.put("token", token);
            response.put("role", role);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

}


