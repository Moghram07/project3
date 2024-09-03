package com.example.project3.Service;

import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.CustomerRepository;
import com.example.project3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void registerCustomer(CustomerDTO customerDTO) {
        User user = new User();
        user.setUsername(customerDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hash);
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setRole("CUSTOMER");
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setPhone(customerDTO.getPhone());
        customerRepository.save(customer);
    }

    public void updateCustomer(Integer id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id " + id + " not found");
        }
        User user = customer.getUser();
        user.setUsername(customerDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hash);
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setRole(customerDTO.getRole());
        userRepository.save(user);

        customer.setPhone(customerDTO.getPhone());
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id " + id + " not found");
        }
        customerRepository.delete(customer);
    }
}
