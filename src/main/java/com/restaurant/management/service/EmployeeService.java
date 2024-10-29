package com.restaurant.management.service;

import com.restaurant.management.model.Employee;
import com.restaurant.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StorageService storageService;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee, MultipartFile file) throws IOException {
        String imageUrl = storageService.uploadImage(file);
        employee.setImage(imageUrl);
        return employeeRepository.save(employee);
    }

    public void updateEmployee(Long id, Employee updatedEmployee, MultipartFile file) throws IOException {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + updatedEmployee.getId()));

        existingEmployee.setId(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setPosition(updatedEmployee.getPosition());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setEmail(updatedEmployee.getEmail());

        if (file != null && !file.isEmpty()) {
            existingEmployee.setImage(storageService.uploadImage(file));
        }

        employeeRepository.save(existingEmployee);
    }


    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
