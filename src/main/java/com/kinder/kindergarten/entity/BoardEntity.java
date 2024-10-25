package com.kinder.kindergarten.entity;

import com.kinder.kindergarten.constant.BoardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "board")
@Getter  @Setter
@ToString
public class BoardEntity extends TimeEntity {

  @Id
  @Column(name = "board_id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String board_id;

  @Column(nullable = false)
  private String board_title;

  @Column(nullable = false)
  @Lob
  private String board_content;

  @Enumerated(EnumType.STRING)
  @Column(name = "board_type")
  private BoardType boardType;

  @Column(nullable = false)
  private String board_writer;

  @ManyToOne(optional = true)
  @JoinColumn(name="file_id", nullable = true)
  private BoardFileEntity boardFileEntity;

  @ColumnDefault("0")
  @Column(nullable = false)
  private Integer views;

  @ColumnDefault("0")
  @Column(nullable = false)
  private Integer likes;

}
