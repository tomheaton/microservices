package dev.tomheaton.microservices.employee;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super(String.format("Could not find Employee with id: %s", id));
    }
}
