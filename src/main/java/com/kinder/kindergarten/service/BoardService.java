package com.kinder.kindergarten.service;


import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import com.kinder.kindergarten.DTO.BoardDTO;
import com.kinder.kindergarten.DTO.BoardFormDTO;
import com.kinder.kindergarten.constant.BoardType;
import com.kinder.kindergarten.entity.BoardEntity;
import com.kinder.kindergarten.entity.BoardFileEntity;
import com.kinder.kindergarten.repository.BoardFileRepository;
import com.kinder.kindergarten.repository.BoardRepository;
import com.kinder.kindergarten.repository.QueryDSL;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class BoardService {

private final BoardRepository boardRepository;

  private final BoardFileRepository boardFileRepository;

  private final QueryDSL queryDSL;

  private final ModelMapper modelMapper;

  private final FileService fileService;

  //application.properties 에 있는 값
  @Value("${uploadPath1}")
  private String uploadPath;

  public Page<BoardDTO> getCommonBoards(Pageable pageable) {
    log.info("페이지 불러오기 - BoardService.getCommonBoards()실행. pageable 정보 : " +pageable);

    //페이징 처리
    Page<BoardEntity> boardPage = boardRepository.findByBoardType(BoardType.COMMON, pageable);

    return boardPage.map(boardEntity -> modelMapper.map(boardEntity, BoardDTO.class));
  }

  @Transactional
  public void saveBoard(BoardFormDTO boardFormDTO) throws Exception{
    Ulid ulid = UlidCreator.getUlid();
    String id = ulid.toString();
    boardFormDTO.setBoardId(id); // UUID대신 사용할 ULID
    BoardEntity board = boardFormDTO.wirteBoard();
    boardRepository.save(board);
    log.info("게시글+파일 저장 - BoardService.saveBoard() 실행" + boardFormDTO);
  }

  public void saveBoardWithFile(BoardFormDTO boardFormDTO, List<MultipartFile> boardFileList) throws Exception{
    log.info("게시글+파일 저장 - BoardService.saveBoardWithFile() 실행" + boardFormDTO);

    Ulid ulid = UlidCreator.getUlid();
    String id = ulid.toString();
    boardFormDTO.setBoardId(id); // UUID대신 사용할 ULID
    BoardEntity board = boardFormDTO.wirteBoard();

    if (boardFileList != null && !boardFileList.get(0).isEmpty()) {
      BoardFileEntity boardFile = new BoardFileEntity();

      StringBuilder originalNames = new StringBuilder();
      StringBuilder modifiedNames = new StringBuilder();
      StringBuilder filePaths = new StringBuilder();

      String mainFile = "";
      boolean isFirstImage = true;

      for (MultipartFile file : boardFileList) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // FileService를 사용하여 파일 저장
        String savedFileName = fileService.uploadFile(uploadPath, originalFilename, file.getBytes());
        String filePath = fileService.getFullPath(savedFileName);

        // 구분자로 이름들을 연결
        originalNames.append(originalFilename).append(",");
        modifiedNames.append(savedFileName).append(",");
        filePaths.append(filePath).append(",");

        // 이미지 파일인 경우 첫 번째 파일을 main_file로 지정
        if (isFirstImage && isImageFile(extension)) {
          mainFile = savedFileName;
          isFirstImage = false;
        }
      }

      // 마지막 쉼표 제거
      originalNames.setLength(originalNames.length() - 1);
      modifiedNames.setLength(modifiedNames.length() - 1);
      filePaths.setLength(filePaths.length() - 1);

      // BoardFileEntity 설정
      boardFile.setOriginalName(originalNames.toString());
      boardFile.setModifiedName(modifiedNames.toString());
      boardFile.setFilePath(filePaths.toString());
      boardFile.setMainFile(mainFile);
      Ulid ulid2 = UlidCreator.getUlid();
      boardFile.setFileId(ulid2.toString());
      
      //게시물 정보 저장
      boardRepository.save(board);
      //파일 정보 저장
      boardFileRepository.save(boardFile);

    }

    
    log.info("게시글 저장 - BoardService.saveBoard : "+ boardFormDTO);
  }

  //이미지 파일인지 확인하는 method
  private boolean isImageFile(String extension) {
    return Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp")
            .contains(extension.toLowerCase());
  }

  //페이지 상세보기 board_id로 찾아서 ModelMapper사용(entity -> DTO 로)
  public Optional<BoardDTO> getBoard(String board_id){
    return boardRepository.findById(board_id).map(boardEntity -> modelMapper.map(boardEntity,BoardDTO.class));
  }

}//class end
