package com.project.boardproject.api.board.service;

import com.project.boardproject.api.board.mapper.BoardMapper;
import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.PaginationVO;
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

    // 페이징을 위한 전체 데이터 개수 파악
    public int getCount() {
        return boardMapper.getCount();
    }


    // 페이징을 위한 getListPage 메소드 추가
    public List<BoardDTO> getListPage(final PaginationVO paginationVO) {
        return boardMapper.getListPage(paginationVO);
    }




}
