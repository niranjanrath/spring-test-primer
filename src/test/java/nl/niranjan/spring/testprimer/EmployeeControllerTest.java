package nl.niranjan.spring.testprimer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeBusiness employeeBusiness;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void getAllEmployeesWhenOneEmployee() throws Exception {
        UUID empUuid = UUID.randomUUID();
        Mockito.when(employeeBusiness.getAllEmployees()).thenReturn(Arrays.asList(Employee.builder()
                .id(empUuid).name("Niranjan Rath").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{'id':'" + empUuid + "','name':'Niranjan Rath'}]"));
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getAllEmployees();
    }

    @Test
    void getAllEmployeesWhenNoEmployee() throws Exception {
        Mockito.when(employeeBusiness.getAllEmployees()).thenReturn(Arrays.asList());
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[]"));
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getAllEmployees();
    }

    @Test
    void getAllEmployeesWhenNull() throws Exception {
        Mockito.when(employeeBusiness.getAllEmployees()).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getAllEmployees();
    }

    @Test
    void getAllEmployeesWhenException() throws Exception {
        Mockito.when(employeeBusiness.getAllEmployees()).thenThrow(RuntimeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getAllEmployees();
    }

    @Test
    void getAllEmployeesWhenNullpointerException() throws Exception {
        Mockito.when(employeeBusiness.getAllEmployees()).thenThrow(NullPointerException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getAllEmployees();
    }

    @Test
    void getAllEmployeesWhenMultipleEmployees() throws Exception {
        UUID empUuid1 = UUID.randomUUID();
        UUID empUuid2 = UUID.randomUUID();
        Mockito.when(employeeBusiness.getAllEmployees())
                .thenReturn(Arrays.asList(
                        Employee.builder()
                                .id(empUuid1)
                                .name("Employee1")
                                .build(),
                        Employee.builder()
                                .id(empUuid2)
                                .name("Employee2")
                                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{'id':'" + empUuid1 + "','name':'Employee1'}," +
                        "{'id':'" + empUuid2 + "','name':'Employee2'}" +
                        "]"));
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getAllEmployees();
    }

    @Test
    void getEmployeeByIdWhenAvailableInDatabase() throws Exception {
        UUID empUuid = UUID.randomUUID();
        Mockito.when(employeeBusiness.getEmployeeById(empUuid)).thenReturn(Optional.of(Employee.builder()
                .id(empUuid).name("Employee 1").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + empUuid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{'id':'" + empUuid + "','name':'Employee 1'}"));
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(empUuid);
    }

    @Test
    void getEmployeeByIdWhenNotAvailableInDatabase() throws Exception {
        UUID empUuid = UUID.randomUUID();
        Mockito.when(employeeBusiness.getEmployeeById(empUuid)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + empUuid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(empUuid);
    }

    @Test
    void getEmployeeByIdWhenNullValueReturned() throws Exception {
        UUID empUuid = UUID.randomUUID();
        Mockito.when(employeeBusiness.getEmployeeById(empUuid)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + empUuid))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(empUuid);
    }

    @Test
    void getEmployeeByIdWhenExceptionThrown() throws Exception {
        UUID empUuid = UUID.randomUUID();
        Mockito.when(employeeBusiness.getEmployeeById(empUuid)).thenThrow(RuntimeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + empUuid))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(empUuid);
    }

    @Test
    void createNewEmployee() throws Exception {
        UUID empUuid = UUID.randomUUID();
        Employee savedEmployee = Employee.builder()
                .id(empUuid).name("Employee New").build();
        Mockito.when(employeeBusiness.createNewEmployee(Mockito.any())).thenReturn(savedEmployee);
        mockMvc.perform(MockMvcRequestBuilders.post("/employees", Employee.builder().name("Employee New").build()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).createNewEmployee(Mockito.any());
    }
}