package com.project.boardproject.api.board.controller;

import com.project.boardproject.api.board.model.BoardDTO;
import com.project.boardproject.api.board.model.PaginationVO;
import com.project.boardproject.api.board.service.BoardService;
import com.project.boardproject.api.image.model.ImageDTO;
import com.project.boardproject.api.image.service.ImageService;
import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/writeBoardPage")
    public String writeBoardPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("userInfo"));
        session.getAttribute("userInfo");
        return "writeboard";
    }

    @PostMapping("/writeBoard")
    public String writeBoard(HttpServletRequest request, BoardDTO board, final Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        HttpSession session = request.getSession();
        ImageDTO imageDTO = new ImageDTO();
        UserDTO userDTO = (UserDTO) session.getAttribute("userInfo");
        board.setWriter(userDTO.getNickName());
        board.setUId(userDTO.getUId());

        int targetNum = board.getBId();
        String tag = board.getContent();
        int typeNUm = 1;

        while (true) {
            if (tag.indexOf("img/") >= 0) {
                System.out.println("tag: " + tag);
                // 이미지 번호 세팅
                imageDTO.setTargetNum(targetNum);
                // 이미지 구분 번호 세팅
                imageDTO.setTypeNum(typeNUm);
                //이미지 이름 세팅 (이미지 태그 값 잘라내기)
                String imageUrl = tag.substring(tag.indexOf("img/"), tag.indexOf("\" style="));
                System.out.println(imageUrl);
                imageDTO.setImageName(imageUrl);
                // 이미지 추가
                imageService.insertImage(imageDTO);
                // 찾은 부분까지 잘라내고 다시 찾기 위해 저장
                tag = tag.substring(tag.indexOf("\" style=") + 9);
                typeNUm++;
            } else {
                break;
            }
            PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

            List<BoardDTO> list = this.boardService.getListPage(paginationVO);
            boardService.writeBoard(board);
            model.addAttribute("boardList", list);
            model.addAttribute("page", page);
            model.addAttribute("pageVO", paginationVO);

            return "index";
        }

        System.out.println("targetNum: " + targetNum);
        model.addAttribute("boardNum", targetNum);
        return "index";
    }

    @GetMapping("/boardList")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "1") final int page) {
        PaginationVO paginationVO = new PaginationVO(this.boardService.getCount(), page); // 모든 게시글 개수 구하기.

        List<BoardDTO> list = this.boardService.getListPage(paginationVO);

        model.addAttribute("boardList", list);
        model.addAttribute("page", page);
        model.addAttribute("pageVO", paginationVO);
        return "index";
    }

    @GetMapping("/boardDetail")
    public String boardDetail(BoardDTO board, Model model, @RequestParam(value = "bid") final int bid) {
        board.setBId(bid);
        model.addAttribute("boardInfo", boardService.boardDetail(board));
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

}
