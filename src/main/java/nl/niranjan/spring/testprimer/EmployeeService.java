package nl.niranjan.spring.testprimer;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    Iterable<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long empUuid);

    Employee saveEmployee(Employee employee);
}
