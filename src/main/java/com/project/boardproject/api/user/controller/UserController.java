package com.project.boardproject.api.user.controller;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.PaginationVO;
import com.project.boardproject.api.board.service.BoardService;
import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String index(final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);


        return "index";
    }

    @GetMapping("registerPage")
    public String registerPage() {

        return "register";
    }

    @PostMapping("/checkId")
    @ResponseBody
    public String checkId(UserDTO user) {
        if(userService.checkId(user) == null) {
            System.out.println("중복되지 않음");
            return "1";
        }
        return "2";
    }

    /**
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(UserDTO user, final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        try {
            userService.register(user);
            PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

            List<BoardDTO> list = this.boardService.getListPage(paginationVO);

            model.addAttribute("boardList", list);
            model.addAttribute("page", page);
            model.addAttribute("pageVO", paginationVO);

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
    public String getUsers(final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("users", userService.getUsers());

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);

        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(UserDTO user, HttpServletRequest request, final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        // 세션에 요청 값 저장
        HttpSession session = request.getSession();
        UserDTO userInfo = userService.login(user);
        // 세션에 로그인 정보 저장
        session.setAttribute("userInfo", userInfo);

        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);


        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        session.invalidate();

        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);

        return "index";
    }

}
