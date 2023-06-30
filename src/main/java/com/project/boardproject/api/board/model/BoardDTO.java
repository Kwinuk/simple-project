package com.project.boardproject.api.board.model;

import lombok.Data;

@Data
public class BoardDTO {
    private int bId;
    private int uId;
    private String title;
    private String content;
    private String writer;
    private String writeDate;
    private String viewCnt;
    private String rowNum;
}
