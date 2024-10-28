package com.kinder.kindergarten.entity;

import com.kinder.kindergarten.constant.Employee.Position;
import com.kinder.kindergarten.dto.Employee.EmployeeDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Table(name = "Employee")
@Entity
@Getter @Setter
@ToString
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 직원 기본키

    @Column(name = "employee_cleanup", nullable = false, unique = true)
    private String cleanup; // 직원 사번

    @Column(name = "employee_name", nullable = false)
    private String name; // 직원 이름

    @Column(name = "employee_email", nullable = false, unique = true)
    private String email; // 직원 이메일

    @Column(name = "employee_password", nullable = false)
    private String password; // 직원 비밀번호

    @Column(name = "employee_phone", nullable = false, unique = true)
    private String phone; // 직원 연락처

    @Column(name = "employee_position")
    @Enumerated(EnumType.STRING)
    private Position position; // 직원 직위

    @Column(name = "employee_department")
    private String department; // 직원 부서

    @Column(name = "employee_status")
    private String status; // 재직상태

    @Column(name = "employee_salary", nullable = false)
    private BigDecimal salary; // 직원 급여

    @Column(name = "employee_hireDate")
    private LocalDate hireDate; // 입사날짜

    // 사번의 자동번호 생성자
    private static AtomicInteger autoNumberGenerator = new AtomicInteger(1);

    public static Employee createEmployee(EmployeeDTO employeeDTO, PasswordEncoder passwordEncoder){

        // 사번에 적용될 현재날짜
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 사번에 적용될 자동번호
        int autoNumber = autoNumberGenerator.getAndIncrement();

        Employee employee = new Employee();
        employee.setCleanup(currentDate+autoNumber);
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        String password = passwordEncoder.encode(employeeDTO.getPassword());
        employee.setPassword(password);
        employee.setPhone(employeeDTO.getPhone());
        employee.setPosition(employee.getPosition());
        employee.setDepartment(employee.getDepartment());
        employee.setStatus(employee.getStatus());
        employee.setSalary(employeeDTO.getSalary());
        employee.setHireDate(employeeDTO.getHireDate());
        return employee;
    }
}
