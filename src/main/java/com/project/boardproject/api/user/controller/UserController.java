package com.project.boardproject.api.user.controller;

import com.project.boardproject.api.board.model.Criteria;
import com.project.boardproject.api.board.service.BoardService;
import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("userInfo")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;

    /**
     * @return
     */
    @GetMapping("/index")
    public String index(Model model ) {
        model.addAttribute("boardList", boardService.boardList());
        return "index";
    }

    @GetMapping("registerPage")
    public String registerPage() {

        return "register";
    }

    /**
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(UserDTO user, Model model) {
        try {
            userService.register(user);
            model.addAttribute("boardList", boardService.boardList());
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param model
     * @return
     */
    @GetMapping("/userlist")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(UserDTO user, HttpServletRequest request, Model model ) {
        // 세션에 요청 값 저장
        HttpSession session = request.getSession();
        UserDTO userInfo = userService.login(user);
        // 세션에 로그인 정보 저장
        session.setAttribute("userInfo", userInfo);
        model.addAttribute("boardList", boardService.boardList());

        System.out.println(session.getAttribute("userInfo"));

        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "index";
    }

}
