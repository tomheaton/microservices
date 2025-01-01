package dev.tomheaton.microservices.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = this.service.getEmployees();

        return ResponseEntity.ok().body(employees);
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee addedEmployee = this.service.addEmployee(employee);

        return ResponseEntity.ok().body(addedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Employee employee = this.service.getEmployee(id);

        return ResponseEntity.ok().body(employee);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = this.service.updateEmployee(id, employee);

        return ResponseEntity.ok().body(updatedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> replaceEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee replacedEmployee = this.service.replaceEmployee(id, employee);

        return ResponseEntity.ok().body(replacedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        this.service.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}
