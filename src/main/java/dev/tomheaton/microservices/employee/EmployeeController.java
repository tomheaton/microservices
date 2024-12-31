package dev.tomheaton.microservices.employee;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public List<Employee> getEmployees() {
        return this.repository.findAll();
    }

    @PostMapping("/")
    public Employee addEmployee(@RequestBody Employee employee) {
        return this.repository.save(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return this.repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return this.repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return this.repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return this.repository.save(newEmployee);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}
