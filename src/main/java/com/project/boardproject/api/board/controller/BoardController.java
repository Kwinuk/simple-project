package com.project.boardproject.api.board.controller;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.PaginationVO;
import com.project.boardproject.api.board.service.BoardService;
import com.project.boardproject.api.image.model.ImageDTO;
import com.project.boardproject.api.image.service.ImageService;
import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import com.project.boardproject.api.view.model.ViewDTO;
import com.project.boardproject.api.view.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;
    @Autowired
    ViewService viewService;

    @GetMapping("/insertBoardPage")
    public String insertBoardPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("userInfo"));
        session.getAttribute("userInfo");
        return "insertBoard";
    }

    @PostMapping("/insertBoard")
    public String insertBoard(HttpServletRequest request, BoardDTO board, final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        HttpSession session = request.getSession();
        ImageDTO imageDTO = new ImageDTO();
        UserDTO userDTO = (UserDTO) session.getAttribute("userInfo");
        board.setWriter(userDTO.getNickName());
        board.setUId(userDTO.getUId());
        int targetNum = 0;
        String tag = board.getContent();
        int typeNUm = 1;
        if (session.getAttribute("userInfo") != null) {
            System.out.println("로그인 정보 확인");
            if (boardService.insertBoard(board)) {
                System.out.println("게시글 작성 성공");
                int bId = boardService.findBid();
                // 조회수 생성을 위한 변수 생성
                ViewDTO view = new ViewDTO();
                // 게시글의 PK를 가져와 조회수의 FK로 사용하여 조회수 데이터 생성
                view.setBId(bId);
                viewService.insertView(view);
                System.out.println("게시글 조회수 생성");
                while (true) {
                    if (tag.indexOf("board-img/") >= 0) {
                        System.out.println("tag: " + tag);
                        // 이미지 번호 세팅
                        targetNum = bId;
                        imageDTO.setTargetNum(targetNum);
                        // 이미지 구분 번호 세팅
                        imageDTO.setTypeNum(typeNUm);
                        //이미지 이름 세팅 (이미지 태그 값 잘라내기)
                        String imageUrl = tag.substring(tag.indexOf("board-img/"), tag.indexOf("\" style="));
                        System.out.println(imageUrl);
                        imageDTO.setImageName(imageUrl);
                        // 이미지 추가
                        imageService.insertImage(imageDTO);
                        System.out.println("이미지 저장 성공");
                        // 찾은 부분까지 잘라내고 다시 찾기 위해 저장
                        tag = tag.substring(tag.indexOf("\" style=", imageUrl.indexOf("board-img/")) + 9);
//                        tag = tag.substring(imageUrl.indexOf("\" style=") + 9);
                        typeNUm++;
                    }
                    else {
                        break;
                    }
                }
            } else {
                System.out.println("게시글 작성 실패");
            }
        } else {
            System.out.println("로그인 x");
            PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

            List<BoardDTO> list = this.boardService.getListPage(paginationVO);

            model.addAttribute("boardList", list);
            model.addAttribute("page", page);
            model.addAttribute("pageVO", paginationVO);
            return "index";
        }
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);
        return "index";
    }

    @GetMapping("/popularBoard")
    public String popularBoard(HttpSession session, HttpServletRequest request, Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        session = request.getSession();
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.popularBoard(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);
        return "index";
    }
    @GetMapping("/newestBoard")
    public String newestBoard(HttpSession session, HttpServletRequest request, Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        session = request.getSession();
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);
        return "index";
    }

    @GetMapping("/boardDetail")
    public String boardDetail(HttpServletRequest request, HttpSession session, Model model, @RequestParam(value = "bId") final int bId, @RequestParam(value = "viewCnt") final int viewCnt) {
        session = request.getSession();
        // 뷰로 넘겨줄 게시글 상세 정보를 모델 객체 담아 줌
        model.addAttribute("boardInfo", boardService.boardDetail(bId));
        System.out.println("boardInfo" + model.getAttribute("boardInfo"));
        System.out.println("session = "+ session.getAttribute("userInfo"));
//        System.out.println("uid" + session.getAttribute("uid"));
        // 게시글 상세 페이지 방문 시 해당 게시글의 조회수 증가
        ViewDTO view = new ViewDTO();
        // 조회수를 증가 시킬 게시글의 PK를 받아 업데이트 
        view.setBId(bId);
        view.setViewCnt(viewCnt);
        viewService.updateView(view);

        return "boardDetail";
    }

    @GetMapping("/board")
    public String selectListAndPage(Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.
        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);

        return "index";
    }

    @GetMapping("/updateBoardPage")
    public String updateBoardPage(Model model, @RequestParam(value = "bId") final int bId) {
        System.out.println("게시글 수정 페이지");
        System.out.println("bid" + bId);
        model.addAttribute("board", boardService.boardDetail(bId));
        return "updateBoard";
    }

    @PostMapping("/updateBoard")
    public String updateBoard(BoardDTO board, Model model, @RequestParam(value = "page", defaultValue = "1") final int page, @RequestParam(value = "bId") final int bId) {
        if (boardService.updateBoard(bId)) {
            System.out.println("게시글 수정 완료");
            System.out.println("수정 내용: " + board);
        } else {
            System.out.println("게시글 수정 실패");
        }

        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.
        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);

        return "index";
    }

    @RequestMapping(value="/deleteBoard", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateBoardState(BoardDTO board, Model model, @RequestParam(value = "page", defaultValue = "1") final int page, @RequestParam(value = "bId") final int bId) {
        if (boardService.updateBoardState(bId)) {
            System.out.println("게시글 삭제 처리 완료");
        } else {
            System.out.println("게시글 수정 실패");
        }

        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.
        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);

        return "index";
    }

}
