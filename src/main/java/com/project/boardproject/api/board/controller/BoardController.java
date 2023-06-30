package com.project.boardproject.api.board.controller;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.Criteria;
import com.project.boardproject.api.board.model.PageCreate;
import com.project.boardproject.api.board.model.PageVO;
import com.project.boardproject.api.board.service.BoardService;
import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    UserService userService;

    @GetMapping("writeBoardPage")
    public String writeBoardPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("userInfo"));
        session.getAttribute("userInfo");
        return "writeboard";
    }

    @PostMapping("writeBoard")
    public String writeBoard(HttpServletRequest request, BoardDTO board, Model model) {
        HttpSession session = request.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("userInfo");
        board.setWriter(userDTO.getNickName());
        board.setUId(userDTO.getUId());
        boardService.writeBoard(board);
        model.addAttribute("boardList", boardService.boardList());
        return "index";
    }

    @GetMapping("boardList")
    public String boardList(Model model) {
        model.addAttribute("boardList", boardService.boardList());
        return "index";
    }

    @PostMapping("boardDetail")
    public String boardDetail(BoardDTO board, Model model) {
        int bId = board.getBId();
        board.setBId(bId);
        model.addAttribute("boardInfo", boardService.boardDetail(board));
        return "boardDetail";
    }

    @GetMapping("freeList")
    public String getFree(Model model, PageVO vo) {
        PageCreate pc = new PageCreate();
        pc.setPaging(vo);
        pc.setArticleTotalCount(boardService.getTotal(vo));

        System.out.println(pc);

        model.addAttribute("freeList", boardService.getFreeBoard(vo));
        model.addAttribute("pc", pc);

        return "index";
    }

}
