package dev.tomheaton.microservices.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getEmployees() {
        return this.repository.findAll();
    }

    public Employee getEmployee(Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new IllegalStateException("Employee with id " + id + " does not exist.")
        );
    }

    public Employee addEmployee(Employee employee) {
        if (employee.getName() == null || employee.getRole() == null) {
            throw new IllegalStateException("Invalid employee object.");
        }

        Optional<Employee> employeeOptional = this.repository.findEmployeeByName(employee.getName());

        if (employeeOptional.isPresent()) {
            throw new IllegalStateException("Email already exists.");
        }

        return this.repository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = this.repository.findById(id).orElseThrow(
            () -> new IllegalStateException("Employee with id " + id + " does not exist.")
        );

        if (employee.getName() != null) {
            existingEmployee.setName(employee.getName());
        }

        if (employee.getRole() != null) {
            existingEmployee.setRole(employee.getRole());
        }

        return this.repository.save(existingEmployee);
    }

    @Transactional
    public Employee replaceEmployee(Long id, Employee newEmployee) {
        return this.repository.findById(id).map(employee -> {
            if (newEmployee.getName() != null) {
                employee.setName(newEmployee.getName());
            }

            if (newEmployee.getRole() != null) {
                employee.setRole(newEmployee.getRole());
            }

            return this.repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);

            return this.repository.save(newEmployee);
        });
    }

    public void deleteEmployee(Long id) {
        boolean exists = this.repository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("Employee with id " + id + " does not exist.");
        }

        this.repository.deleteById(id);
    }
}
