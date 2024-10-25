package com.kinder.kindergarten.entity;

import com.kinder.kindergarten.constant.Employee.Department;
import com.kinder.kindergarten.constant.Employee.Position;
import com.kinder.kindergarten.constant.Employee.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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
    @Enumerated(EnumType.STRING)
    private Department department; // 직원 부서

    @Column(name = "employee_status")
    @Enumerated(EnumType.STRING)
    private Status status; // 재직상태

    @Column(name = "employee_salary", nullable = false)
    private String salary; // 직원 급여

    @Column(name = "employee_hireDate")
    private LocalDate hireDate; // 입사날짜

}
