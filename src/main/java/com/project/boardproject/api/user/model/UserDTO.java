package com.project.boardproject.api.user.model;

import lombok.Data;

@Data
public class UserDTO {
    private int uId;
    private String userId;
    private String userPw;
    private String userName;
    private String nickName;
    private String address;
    private String phoneNum;
}
