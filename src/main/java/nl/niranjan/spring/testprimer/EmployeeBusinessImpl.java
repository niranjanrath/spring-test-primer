package nl.niranjan.spring.testprimer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeBusinessImpl implements EmployeeBusiness {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public Iterable<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long empUuid){
        return employeeService.getEmployeeById(empUuid);
    }

    @Override
    public Employee createNewEmployee(Employee employee){
        return employeeService.saveEmployee(employee);
    }

}
