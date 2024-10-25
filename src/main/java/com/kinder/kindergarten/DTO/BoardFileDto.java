package com.kinder.kindergarten.DTO;

import lombok.Data;

@Data
public class BoardFileDto {

  // 기본키
  private String file_id;
  
  //원본 이름
  private String orignal_name;

  //수정한 이름
  private String modified_name;
  
  //파일 저장 경로
  private String file_path;
  
  //메인 사진 이름
  private String main_file;

}
