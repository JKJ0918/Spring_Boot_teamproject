package com.kinder.kindergarten.entity;

import com.kinder.kindergarten.constant.Employee.Status;
import com.kinder.kindergarten.constant.Employee.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Table(name = "Employee_Leave")
@Entity
@Getter @Setter
@ToString
public class Leave {

    @Id
    @Column(name = "leave_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 휴가 기본키

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee; // 직원 기본키

    @Column(name = "leave_start")
    private LocalDate start; // 휴가 시작 날짜

    @Column(name = "leave_end")
    private LocalDate end; // 휴가 종료 날짜

    @Column(name = "leave_type")
    @Enumerated(EnumType.STRING)
    private Type type; // 휴가 유형

    @Column(name = "leave_status")
    @Enumerated(EnumType.STRING)
    private Status status; // 승인여부
}
