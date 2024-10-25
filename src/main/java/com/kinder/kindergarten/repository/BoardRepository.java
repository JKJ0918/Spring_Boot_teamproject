package com.kinder.kindergarten.repository;

import com.kinder.kindergarten.constant.BoardType;
import com.kinder.kindergarten.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,String> {

  //게시판 타입별로 전체 불러오기
  Page<BoardEntity> findByBoardType(BoardType boardType, Pageable pageable);
  
}
