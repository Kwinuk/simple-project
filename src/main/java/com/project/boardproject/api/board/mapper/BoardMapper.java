package com.project.boardproject.api.board.mapper;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.Criteria;
import com.project.boardproject.api.board.model.PageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void writeBoard(BoardDTO board);

    List<BoardDTO> boardList();

    BoardDTO boardDetail(BoardDTO board);

    int getTotal(PageVO pageVO);

    List<BoardDTO> getFreeBoard(PageVO pageVO);
}
