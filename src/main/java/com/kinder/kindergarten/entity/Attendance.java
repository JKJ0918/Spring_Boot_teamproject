package com.kinder.kindergarten.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "Attendance")
@Entity
@Getter @Setter
@ToString
public class Attendance {

    @Id
    @Column(name = "attendance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 근태관리 기본키

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee; // 직원 기본키

    @Column(name = "attendance_date")
    private LocalDate date; // 출근날짜

    @Column(name = "attendance_checkIn")
    private LocalTime checkIn; // 출근시간

    @Column(name = "attendance_checkOut")
    private LocalTime checkOut; // 퇴근시간

    @Column(name = "attendance_status")
    private String status; // 출퇴근 상태

}
