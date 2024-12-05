package com.restaurant.management.service;

import com.restaurant.management.model.Category;
import com.restaurant.management.model.Customer;
import com.restaurant.management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
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

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Long getCustomerIdByEmail(String email) {
        return customerRepository.getCustomerIdByEmail(email);
    }
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
