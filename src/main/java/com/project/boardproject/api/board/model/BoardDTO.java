package com.project.boardproject.api.board.model;

import lombok.Data;

@Data
public class BoardDTO {
    private int bId;
    private String title;
    private String content;
    private String writer;
    private String writeDate;
    private String viewCnt;
}
