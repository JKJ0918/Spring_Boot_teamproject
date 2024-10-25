package com.kinder.kindergarten.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Table(name = "Certificate")
@Entity
@Getter @Setter
@ToString
public class Certificate {

    @Id
    @Column(name = "certificate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자격증 기본키

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee; // 교사 기본키

    @Column(name = "certificate_name")
    private String name; // 자격증 이름

    @Column(name = "certificate_issued")
    private LocalDate issued; // 자격증 발급날짜

    @Column(name = "certificate_expri")
    private LocalDate expri; // 자격증 만료일



}
