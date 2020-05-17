package nl.niranjan.spring.testprimer;

import java.util.Optional;

public interface EmployeeBusiness {
    Iterable<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long empUuid);

    Employee createNewEmployee(Employee employee);
}
