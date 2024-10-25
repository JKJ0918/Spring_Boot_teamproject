package com.kinder.kindergarten.service;


import com.kinder.kindergarten.DTO.BoardDto;
import com.kinder.kindergarten.constant.BoardType;
import com.kinder.kindergarten.entity.BoardEntity;
import com.kinder.kindergarten.repository.BoardRepository;
import com.kinder.kindergarten.repository.QueryDSL;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

private final BoardRepository boardRepository;

  private final QueryDSL queryDSL;

  private final ModelMapper modelMapper;

  public Page<BoardDto> getCommonBoards(Pageable pageable) {

    //페이징 처리
    Page<BoardEntity> boardPage = boardRepository.findByBoardType(BoardType.COMMON, pageable);

    return boardPage.map(boardEntity -> modelMapper.map(boardEntity, BoardDto.class));
  }


}//class end
