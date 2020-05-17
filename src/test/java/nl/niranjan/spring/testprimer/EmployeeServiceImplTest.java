package nl.niranjan.spring.testprimer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void getAllEmployeesNullValue() {
        Mockito.when(employeeRepository.findAll()).thenReturn(null);
        Iterable<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).isNullOrEmpty();
    }

    @Test
    void getAllEmployeesWithValues() {
        Mockito.when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(Employee.builder()
                        .id(Long.parseLong("1")).name("test name").build()));
        Iterable<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).isNotEmpty();
    }

    @Test
    void getAllEmployeesWithException() {
        Mockito.when(employeeRepository.findAll())
                .thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> {
            employeeService.getAllEmployees();
        });
    }

    @Test
    void getEmployeeByIdNullValue() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(null);
        Assertions.assertThat(employeeService.getEmployeeById(Long.parseLong("1"))).isNull();
    }

    @Test
    void getEmployeeByIdGoodValue() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Employee.builder()
                        .id(Long.parseLong("1")).name("Employee 1").build()));
        Assertions.assertThat(employeeService.getEmployeeById(Long.parseLong("1"))).isNotEmpty();
    }

    @Test
    void getEmployeeByIdWithException() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong()))
                .thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeById(Long.parseLong("1"));
        });
    }

    @Test
    void saveEmployeeHF() {
        Mockito.when(employeeRepository.save(Mockito.any()))
                .thenReturn(Employee.builder()
                        .id(Long.parseLong("1")).name("Employee 1").build());
        Assertions.assertThat(employeeService.saveEmployee(Mockito.any())).isNotNull();
    }

    @Test
    void saveEmployeeWithException() {
        Mockito.when(employeeRepository.save(Mockito.any()))
                .thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> {
            employeeService.saveEmployee(Employee.builder().id(Long.parseLong("1")).name("Employee 1").build());
        });
    }
}