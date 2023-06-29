package com.project.boardproject.api.board.controller;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.service.BoardService;
import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    UserService userService;

    @GetMapping("writeBoardPage")
    public String writeBoardPage(HttpSession session) {
        System.out.println(session.getAttribute("userInfo"));
        return "writeboard";
    }

    @PostMapping("writeBoard")
    public String writeBoard(BoardDTO board, Model model) {
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
        model.addAttribute("boardInfo", boardService.boardDetail(board));
        return "boardDetail";
    }
}
