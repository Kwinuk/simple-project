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

    public boolean insertBoard(BoardDTO board) {
        boardMapper.insertBoard(board);
        return true;
    }

    public List<BoardDTO> boardList() {
        return boardMapper.boardList();
    }

    public BoardDTO boardDetail(int bid) {
        return boardMapper.boardDetail(bid);
    }

    // 페이징을 위한 전체 데이터 개수 파악
    public int getCount() {
        return boardMapper.getCount();
    }


    // 페이징을 위한 getListPage 메소드 추가
    public List<BoardDTO> getListPage(final PaginationVO paginationVO) {
        return boardMapper.getListPage(paginationVO);
    }

    public boolean updateBoard(int bId) {
        return boardMapper.updateBoard(bId);
    }

    public int findBid() {
        return boardMapper.findBid();
    }

    public boolean updateBoardState (int bId) {
        return boardMapper.updateBoardState(bId);
    }

    public List<BoardDTO> popularBoard(final PaginationVO paginationVO) {
        return boardMapper.popularBoard(paginationVO);
    }


}
