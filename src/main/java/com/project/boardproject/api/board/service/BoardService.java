package com.project.boardproject.api.board.service;

import com.project.boardproject.api.board.mapper.BoardMapper;
import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.Criteria;
import com.project.boardproject.api.board.model.PageVO;
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

    public List<BoardDTO> boardList() {
        return boardMapper.boardList();
    }

    public BoardDTO boardDetail(BoardDTO board) {
        return boardMapper.boardDetail(board);
    }

    public int getTotal(PageVO pageVO) {
        return boardMapper.getTotal(pageVO);
    }

    public List<BoardDTO> getFreeBoard(PageVO pageVO) {
        return boardMapper.getFreeBoard(pageVO);
    }


}
