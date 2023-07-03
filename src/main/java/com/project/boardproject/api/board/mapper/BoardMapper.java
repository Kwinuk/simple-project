package com.project.boardproject.api.board.mapper;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.PaginationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void writeBoard(BoardDTO board);

    List<BoardDTO> boardList();

    BoardDTO boardDetail(BoardDTO board);

    int getCount();

    List<BoardDTO> getListPage(PaginationVO paginationVO);
}
