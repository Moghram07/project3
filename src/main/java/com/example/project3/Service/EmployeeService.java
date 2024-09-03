package com.example.project3.Service;

import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.EmployeeRepository;
import com.example.project3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void registerEmployee(EmployeeDTO employeeDTO) {
        User user = new User();
        user.setUsername(employeeDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hash);
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());
        user.setRole("EMPLOYEE");
        userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeRepository.save(employee);

    }

    public void updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with id " + id + " not found");
        }
        User user = employee.getUser();
        user.setUsername(employeeDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hash);
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());
        user.setRole(employeeDTO.getRole());
        userRepository.save(user);

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with id " + id + " not found");
        }
        employeeRepository.delete(employee);
    }
}
