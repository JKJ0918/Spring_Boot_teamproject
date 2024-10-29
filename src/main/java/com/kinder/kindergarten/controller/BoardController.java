package com.kinder.kindergarten.controller;

import com.kinder.kindergarten.DTO.BoardDTO;
import com.kinder.kindergarten.DTO.BoardFormDTO;
import com.kinder.kindergarten.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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

    Page<BoardDTO> boardDtoPage = boardService.getCommonBoards(pageable);

    model.addAttribute("boards", boardDtoPage);
    model.addAttribute("currentPage", pageNum);
    model.addAttribute("totalPages", boardDtoPage.getTotalPages());

    return "board/basic";
  }

  @GetMapping(value="/write")
  public String writeBoard(Model model){
    model.addAttribute("boardFormDTO",new BoardFormDTO());
    return "board/write";
  }


  @PostMapping(value="/write")
  public String postWriteBoard(@Valid BoardFormDTO boardFormDTO, BindingResult bindingResult,
                               Model model, @RequestParam(value = "boardFile", required = false) List<MultipartFile> boardFileList) {
    try {
      if (bindingResult.hasErrors()) {
        return "board/write";
      }

      // 테스트용 작성자 설정 (나중에 실제 인증 정보로 변경)
      boardFormDTO.setBoardWriter("테스트작성자");

      // 파일 존재 여부 확인
      if (boardFileList != null && !boardFileList.isEmpty() && !boardFileList.get(0).isEmpty()) {
        log.info("boardService.saveBoardWithFile() 실행");
        boardService.saveBoardWithFile(boardFormDTO, boardFileList);
      } else {
        log.info("boardService.saveBoard() 실행");
        boardService.saveBoard(boardFormDTO);
      }

      // 게시판 타입에 따른 리다이렉트 처리
      String type = switch(boardFormDTO.getBoardType()) {
        case COMMON -> "basic";
        case DIARY -> "diary";
        case RESEARCH -> "research";
        case NOTIFICATION -> "notification";
      };

      return "redirect:/board/" + type;

    } catch (Exception e) {
      log.error("게시글 등록 중 에러 발생: ", e);
      model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생했습니다.");
      return "board/write";
    }
  }

  @GetMapping(value="/{board_id}")
  private String getBoard(@PathVariable String board_id, Model model){
    model.addAttribute("boardDTO",boardService.getBoard(board_id));
    return "board/get";
  }

}
