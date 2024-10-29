package com.kinder.kindergarten.entity;

import com.kinder.kindergarten.constant.BoardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Entity
@Table(name = "board")
@Getter  @Setter
@ToString
@DynamicInsert //insert시 null인 필드는 제외하기 ( @ColumnDefault 적용)
public class BoardEntity extends TimeEntity {


  @Id
  @Column(name = "board_id")
  private String boardId;

  @Column(nullable = false)
  private String boardTitle;

  @Column(nullable = false)
  @Lob
  private String boardContents;

  @Enumerated(EnumType.STRING)
  @Column(name = "board_type")
  private BoardType boardType;

  @Column(nullable = false)
  private String boardWriter;

  @ColumnDefault("0")
  private Integer views;

  @ColumnDefault("0")
  private Integer likes;

}
