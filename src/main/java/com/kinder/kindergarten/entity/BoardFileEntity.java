package com.kinder.kindergarten.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;

@Entity
@Table(name = "board_file")
@Getter
@Setter
@ToString
public class BoardFileEntity extends TimeEntity {

  @Id
  @Column(name = "file_id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String file_id;

  @Column(nullable = false)
  private String original_name;

  @Column(nullable = false)
  private String modified_name;

  @Column(nullable = false)
  private String file_path;

  @Column(nullable = false)
  private String main_file;

}
