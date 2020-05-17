package nl.niranjan.spring.testprimer;

import java.util.Optional;

public interface EmployeeService {
    Iterable<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long empUuid);

    Employee saveEmployee(Employee employee);
}
