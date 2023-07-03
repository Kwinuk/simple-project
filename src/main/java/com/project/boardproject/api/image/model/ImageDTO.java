package com.project.boardproject.api.image.model;

import lombok.Data;

@Data
public class ImageDTO {
    private int imageNum; // 이미지 PK
    private int targetNum; // 게시판 혹은 상품 데이터의 PK
    private int typeNum; // 이미지 사용하는 테이블 구분
    private String imageName; // 이미지 파일 이름

}
