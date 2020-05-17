package nl.niranjan.spring.testprimer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringTestPrimerApplicationTests {

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private EmployeeBusiness employeeBusiness;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void contextLoads() {
        Assertions.assertThat(employeeController).isNotNull();
        Assertions.assertThat(employeeBusiness).isNotNull();
        Assertions.assertThat(employeeService).isNotNull();
        Assertions.assertThat(employeeRepository).isNotNull();
    }

}
