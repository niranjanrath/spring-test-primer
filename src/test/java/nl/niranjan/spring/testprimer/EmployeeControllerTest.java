package nl.niranjan.spring.testprimer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private EmployeeBusiness employeeBusiness;

    @Test
    void getAllEmployeesWhenOneEmployee() throws Exception {
        Mockito.when(employeeBusiness.getAllEmployees()).thenReturn(Arrays.asList(Employee.builder()
                .id(Long.parseLong("1")).name("Niranjan Rath").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{'id':1,'name':'Niranjan Rath'}]"));
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
        Mockito.when(employeeBusiness.getAllEmployees())
                .thenReturn(Arrays.asList(
                        Employee.builder()
                                .id(Long.parseLong("1"))
                                .name("Employee1")
                                .build(),
                        Employee.builder()
                                .id(Long.parseLong("2"))
                                .name("Employee2")
                                .build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{'id':1,'name':'Employee1'}," +
                        "{'id':2,'name':'Employee2'}" +
                        "]"));
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getAllEmployees();
    }

    @Test
    void getEmployeeByIdWhenAvailableInDatabase() throws Exception {
        Mockito.when(employeeBusiness.getEmployeeById(Long.parseLong("1"))).thenReturn(Optional.of(Employee.builder()
                .id(Long.parseLong("1")).name("Employee 1").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("{'id':1,'name':'Employee 1'}"));
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(Mockito.anyLong());
    }

    @Test
    void getEmployeeByIdWhenNotAvailableInDatabase() throws Exception {
        Long empId1 = new Random().nextLong();
        Mockito.when(employeeBusiness.getEmployeeById(empId1)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + empId1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(Mockito.anyLong());
    }

    @Test
    void getEmployeeByIdWhenNullValueReturned() throws Exception {
        Long empId1 = new Random().nextLong();
        Mockito.when(employeeBusiness.getEmployeeById(empId1)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + empId1))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(Mockito.anyLong());
    }

    @Test
    void getEmployeeByIdWhenExceptionThrown() throws Exception {
        Long empId1 = new Random().nextLong();
        Mockito.when(employeeBusiness.getEmployeeById(empId1)).thenThrow(RuntimeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/" + empId1))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).getEmployeeById(Mockito.anyLong());
    }

    @Test
    void createNewEmployeeHappyFlow() throws Exception {
        Employee savedEmployee = Employee.builder()
                .id(Long.parseLong("1")).name("Employee New").build();
        Mockito.when(employeeBusiness.createNewEmployee(Mockito.any())).thenReturn(savedEmployee);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Employee.builder().name("Employee New").build())))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).createNewEmployee(Mockito.any());
    }

    @Test
    void createNewEmployeeEmptyBody() throws Exception {
        Employee savedEmployee = Employee.builder()
                .id(Long.parseLong("1")).name("Employee New").build();
        Mockito.when(employeeBusiness.createNewEmployee(Mockito.any())).thenReturn(savedEmployee);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Employee.builder().build())))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.never()).createNewEmployee(Mockito.any());
    }

    @Test
    void createNewEmployeeWhenExceptionThrown() throws Exception {
        Employee savedEmployee = Employee.builder()
                .id(Long.parseLong("1")).name("Employee New").build();
        Mockito.when(employeeBusiness.createNewEmployee(Mockito.any())).thenThrow(RuntimeException.class);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Employee.builder().name("Employee New").build())))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
        Mockito.verify(employeeBusiness, Mockito.atLeastOnce()).createNewEmployee(Mockito.any());
    }
}