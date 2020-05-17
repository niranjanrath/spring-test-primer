package nl.niranjan.spring.testprimer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employees")
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
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable("Id") Long empUuid){
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
            return  employee != null && employee.getName() != null ?
                    ResponseEntity.status(HttpStatus.CREATED).body(employeeBusiness.createNewEmployee(employee)) :
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
