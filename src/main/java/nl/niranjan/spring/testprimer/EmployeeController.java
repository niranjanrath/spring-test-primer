package nl.niranjan.spring.testprimer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeBusiness employeeBusiness;

    @GetMapping()
    public ResponseEntity<Iterable<Employee>> getAllEmployees(){
        try{
            Iterable<Employee> employees = employeeBusiness.getAllEmployees();
            return employees != null ?
                    ResponseEntity.ok().body(employees) :
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{Id}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable("Id") UUID empUuid){
        try{
            Optional<Employee> employee = employeeBusiness.getEmployeeById(empUuid);
            return  employee.isPresent() ?
                    ResponseEntity.ok().body(employee) :
                    ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping()
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        try{
            Employee savedEmployee = employeeBusiness.createNewEmployee(employee);
            return  savedEmployee.getId() != null?
                    ResponseEntity.status(HttpStatus.CREATED).body(employee) :
                    ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
