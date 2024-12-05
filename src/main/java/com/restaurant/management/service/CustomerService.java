package com.restaurant.management.service;

import com.restaurant.management.model.Category;
import com.restaurant.management.model.Customer;
import com.restaurant.management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        Customer existingCustomer = customerRepository.findById(customer.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customer.getCustomerId()));

        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setDateOfBirth(customer.getDateOfBirth());

        return customerRepository.save(existingCustomer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found!"));
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer not found!"));
    }
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer getCustomerByPhone(String phone) {
        Customer customer =  customerRepository.findByPhone(phone);
        return customer != null ? customer : null;
    }
}
