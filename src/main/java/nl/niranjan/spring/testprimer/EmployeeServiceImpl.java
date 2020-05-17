package nl.niranjan.spring.testprimer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Iterable<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long empUuid){
        return employeeRepository.findById(empUuid);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
}
