package com.project.boardproject.api.board.mapper;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.PaginationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    boolean insertBoard(BoardDTO board);

    List<BoardDTO> boardList();

    BoardDTO boardDetail(int bid);

    int getCount();

    List<BoardDTO> getListPage(PaginationVO paginationVO);

    boolean updateBoard(BoardDTO boardDTO);

    int findBid();
}
