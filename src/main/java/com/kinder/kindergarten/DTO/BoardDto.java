package com.kinder.kindergarten.DTO;

import lombok.Data;

@Data
public class BoardDto {

  //기본키
  private String board_id;

  //제목
  private String board_title;

  //게시글 내용
  private String board_content;

  //게시글 유형
  private String board_type;

  //작성자
  private String writer;

  //첨부파일 키
  private String file_id;

  private int views;

  private int likes;

  private String regi_date;

  private String modi_date;

}
