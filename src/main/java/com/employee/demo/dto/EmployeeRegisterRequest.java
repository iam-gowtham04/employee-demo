package com.employee.demo.dto;

import com.employee.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRegisterRequest {
    private String username;
    private String password;
    private Role role;

}