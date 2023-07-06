package com.project.boardproject.api.user.controller;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.PaginationVO;
import com.project.boardproject.api.board.service.BoardService;
import com.project.boardproject.api.image.model.ImageDTO;
import com.project.boardproject.api.image.service.ImageService;
import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import com.project.boardproject.api.view.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;
    @Autowired
    ViewService viewService;
    @Autowired
    ImageService imageService;
    @Autowired
    private PasswordEncoder pwEncoder;

    /**
     * @return
     */
    @GetMapping("/index")
    public String index(BoardDTO board, final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<ImageDTO> imageList = imageService.selectAll();
        List<BoardDTO> list = this.boardService.getListPage(paginationVO);
        model.addAttribute("imageList", imageList);
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
        if (userService.checkId(user) == null) {
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
    public String register(UserDTO user, final Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "page", defaultValue = "1") final int page) {
        System.out.println("회원가입 진입");
        System.out.println("회원가입 정보: " + user);
        String urlBack = request.getParameter("urlBack");
        System.out.println("request.urlBack: " + urlBack);

        if (urlBack == null) {
            session.setAttribute("urlBack", "/registerPage");
        } else {
            if (!urlBack.contains("login") && !urlBack.contains("register")) {
                session.setAttribute("urlBack", urlBack);
            }
        }
        System.out.println("session.urlBack: " + session.getAttribute("urlBack"));

        // 암호화를 위한 변수 생성
        String passWord = "";
        String encodePw = "";

        passWord = user.getUserPw();
        encodePw = pwEncoder.encode(passWord);
        user.setUserPw(encodePw);

        if (userService.register(user)) {
            System.out.println("회원가입 성공");
            PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.
            List<BoardDTO> list = this.boardService.getListPage(paginationVO);

            model.addAttribute("boardList", list);
            model.addAttribute("page", page);
            model.addAttribute("pageVO", paginationVO);
            model.addAttribute("viewCnt", viewService.viewCntAll());

            return "index";
        } else {
            response.setContentType(("text/html; charset=utf-8"));
            PrintWriter out;
            try {
                out = response.getWriter();
                model.addAttribute("msg", "회원가입에 실패하였습니다.");
                model.addAttribute("location", "register");
                return "alert";
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
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
        model.addAttribute("viewCnt", viewService.viewCntAll());

        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(UserDTO user, HttpSession session, HttpServletRequest request, final Model model,
                        @RequestParam(value = "page", defaultValue = "1") final int page) {
        System.out.println("PostMapping(login) 진입");
        System.out.println("로그인 입력 값: " + user);

        // 세션에 요청 값 저장
        session = request.getSession();

        // 페이지 진입 전전 주소
        String urlBack = request.getParameter("urlBack");
        System.out.println("request.urlBack: " + urlBack);

        // 요청된 urlBack 값에 따른 페이지 이동
        if (urlBack == null) {
            session.setAttribute("urlBack", "/index");
        } else {
            if (!urlBack.contains("login") && !urlBack.contains("register")) {
                session.setAttribute("urlBack", urlBack);
            }
        }
        System.out.println("session.urlBack: " + session.getAttribute("urlBack"));

        String passWord = "";
        String encodePw = "";

        // 로그인 수행
        UserDTO userInfo = userService.login(user);

        if (userInfo == null) {
            System.out.println("로그인 실패");
            model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
            model.addAttribute("location", "/loginPage");
            System.out.println(model.getAttribute("msg"));
            System.out.println(model.getAttribute("location"));

            return "alert";
        } else {
            passWord = user.getUserPw();
            System.out.println(passWord);
            encodePw = userInfo.getUserPw();
            System.out.println(encodePw);

            if(pwEncoder.matches(passWord, encodePw)) {
                System.out.println("로그인 성공");
                userInfo.setUserPw(""); // 인코딩된 비밀번호 정보 지움
                // 세션에 로그인 정보 저장
                session.setAttribute("userInfo", userInfo);
                session.setAttribute("userName", userInfo.getNickName());
                // 게시글 수정에 필요한 userId
                session.setAttribute("uid", userInfo.getUId());
                model.addAttribute("location", session.getAttribute("urlBack"));
            }
            else {
                System.out.println("로그인 실패");
                model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
                model.addAttribute("location", "/loginPage");
                System.out.println(model.getAttribute("msg"));
                System.out.println(model.getAttribute("location"));

                return "alert";
            }

            // 인덱스 페이지 페이징 처리
            PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.
            List<BoardDTO> boardList = this.boardService.getListPage(paginationVO);

            model.addAttribute("boardList", boardList);
            model.addAttribute("page", page);
            model.addAttribute("pageVO", paginationVO);
            model.addAttribute("viewCnt", viewService.viewCntAll());

            return "index";
        }
    }

    @RequestMapping(value="/exit", method = {RequestMethod.GET, RequestMethod.POST})
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
