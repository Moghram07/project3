package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getCustomers(){
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    @PostMapping("/register")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTO customerDTO, @AuthenticationPrincipal User user){
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.status(201).body(new ApiResponse("Customer Created"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id, @RequestBody @Valid CustomerDTO customerDTO, @AuthenticationPrincipal User user){
        customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Customer Updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer id, @AuthenticationPrincipal User user){
        customerService.deleteCustomer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Customer Deleted"));
    }
}
