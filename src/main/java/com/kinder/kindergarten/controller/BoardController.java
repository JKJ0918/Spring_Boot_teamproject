package com.kinder.kindergarten.controller;

import com.kinder.kindergarten.DTO.BoardDto;
import com.kinder.kindergarten.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Log4j2
@RequestMapping(value="/board")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping(value="/basic")
  public String getBasicBoard(@RequestParam(required = false, defaultValue = "0", value = "page")int pageNum,
                              Model model) {
    pageNum = pageNum == 0 ? 0 : (pageNum - 1);
    Pageable pageable = PageRequest.of(pageNum, 10); // 한 페이지당 10개의 게시글

    Page<BoardDto> boardDtoPage = boardService.getCommonBoards(pageable);

    model.addAttribute("boards", boardDtoPage);
    model.addAttribute("currentPage", pageNum);
    model.addAttribute("totalPages", boardDtoPage.getTotalPages());

    return "/board/basic";
  }

}
