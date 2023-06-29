package com.project.boardproject.api.board.mapper;

import com.project.boardproject.api.board.model.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    void writeBoard(BoardDTO board);

    <List>BoardDTO boardList();

    BoardDTO boardDetail(BoardDTO board);

}
