package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity getEmployees(){
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid EmployeeDTO employeeDTO, @AuthenticationPrincipal User user){
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Employee Created"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id, @RequestBody @Valid EmployeeDTO employeeDTO, @AuthenticationPrincipal User user){
        employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Employee Updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id, @AuthenticationPrincipal User user){
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(200).body(new ApiResponse("Employee Deleted"));
    }

}
