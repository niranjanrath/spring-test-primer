package nl.niranjan.spring.testprimer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeBusinessImplTest {
    @Autowired
    private EmployeeBusiness employeeBusiness;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void getAllEmployeesNullValue() {
        Mockito.when(employeeService.getAllEmployees()).thenReturn(null);
        Iterable<Employee> employees = employeeBusiness.getAllEmployees();
        Assertions.assertThat(employees).isNullOrEmpty();
    }

    @Test
    void getAllEmployeesWithValues() {
        Mockito.when(employeeService.getAllEmployees())
                .thenReturn(Arrays.asList(Employee.builder()
                        .id(Long.parseLong("1")).name("test name").build()));
        Iterable<Employee> employees = employeeBusiness.getAllEmployees();
        Assertions.assertThat(employees).isNotEmpty();
    }

    @Test
    void getAllEmployeesWithException() {
        Mockito.when(employeeService.getAllEmployees())
                .thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> {
            employeeBusiness.getAllEmployees();
        });
    }

    @Test
    void getEmployeeByIdNullValue() {
        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong())).thenReturn(null);
        Assertions.assertThat(employeeBusiness.getEmployeeById(Long.parseLong("1"))).isNull();
    }

    @Test
    void getEmployeeByIdGoodValue() {
        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong()))
                .thenReturn(Optional.of(Employee.builder()
                        .id(Long.parseLong("1")).name("Employee 1").build()));
        Assertions.assertThat(employeeBusiness.getEmployeeById(Long.parseLong("1"))).isNotEmpty();
    }

    @Test
    void getEmployeeByIdWithException() {
        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong()))
                .thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> {
            employeeBusiness.getEmployeeById(Long.parseLong("1"));
        });
    }

    @Test
    void saveEmployeeHF() {
        Mockito.when(employeeService.saveEmployee(Mockito.any()))
                .thenReturn(Employee.builder()
                        .id(Long.parseLong("1")).name("Employee 1").build());
        Assertions.assertThat(employeeBusiness.createNewEmployee(Mockito.any())).isNotNull();
    }

    @Test
    void saveEmployeeWithException() {
        Mockito.when(employeeService.saveEmployee(Mockito.any()))
                .thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> {
            employeeBusiness.createNewEmployee(Employee.builder().id(Long.parseLong("1")).name("Employee 1").build());
        });
    }
}