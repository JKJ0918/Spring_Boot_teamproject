package com.kinder.kindergarten.controller;

import com.kinder.kindergarten.DTO.BoardDTO;
import com.kinder.kindergarten.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/board")
public class BoardRestController {

  private final BoardService boardService;

  @GetMapping("/sort")
  public ResponseEntity<Page<BoardDTO>> getSortedBoards(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam String sortBy) {

    Sort sort;
    switch (sortBy) {
      case "latest":
        sort = Sort.by(Sort.Direction.DESC, "regi_date");
        break;
      case "views":
        sort = Sort.by(Sort.Direction.DESC, "views");
        break;
      case "likes":
        sort = Sort.by(Sort.Direction.DESC, "likes");
        break;
      default:
        sort = Sort.by(Sort.Direction.DESC, "regi_date");
    }

    Pageable pageable = PageRequest.of(page, 10, sort);
    Page<BoardDTO> boards = boardService.getCommonBoards(pageable);

    return ResponseEntity.ok(boards);
  }

}
