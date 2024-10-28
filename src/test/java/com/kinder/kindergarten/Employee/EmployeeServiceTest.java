package com.kinder.kindergarten.Employee;

import com.kinder.kindergarten.dto.Employee.EmployeeDTO;
import com.kinder.kindergarten.entity.Employee;
import com.kinder.kindergarten.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@Rollback(false)
public class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    PasswordEncoder passwordEncoder;


    public Employee createEmployee(){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("어드민");
        employeeDTO.setEmail("admin@mbc.com");
        employeeDTO.setPassword("123456789");
        employeeDTO.setPhone("01012345678");
        employeeDTO.setSalary("5,000,000");
        employeeDTO.setHireDate(LocalDate.of(2020,10,28));
        return Employee.createEmployee(employeeDTO, passwordEncoder);
    }

    @Test
    public void saveEmployeeTest() throws IllegalAccessException {
        Employee employee = createEmployee();
        Employee saveEmployee = employeeService.saveEmployee(employee);

        assertEquals(employee.getCleanup(), saveEmployee.getCleanup());
        assertEquals(employee.getEmail(), saveEmployee.getEmail());
        assertEquals(employee.getPhone(), saveEmployee.getPhone());
    }

}
