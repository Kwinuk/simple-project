package com.project.boardproject.api.board.mapper;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void writeBoard(BoardDTO board);

    List<BoardDTO> boardList(Criteria criteria);

    BoardDTO boardDetail(BoardDTO board);

}
