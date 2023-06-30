package com.project.boardproject.api.board.service;

import com.project.boardproject.api.board.mapper.BoardMapper;
import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardMapper boardMapper;

    public void writeBoard(BoardDTO board) {
        boardMapper.writeBoard(board);
    }

    public List<BoardDTO> boardList(Criteria criteria) {
        return  boardMapper.boardList(criteria);
    }

    public BoardDTO boardDetail(BoardDTO board) {
        return boardMapper.boardDetail(board);
    }


}
