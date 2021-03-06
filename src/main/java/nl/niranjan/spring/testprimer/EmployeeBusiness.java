package nl.niranjan.spring.testprimer;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeBusiness {
    Iterable<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long empUuid);

    Employee createNewEmployee(Employee employee);
}
