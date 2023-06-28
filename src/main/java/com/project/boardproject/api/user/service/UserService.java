package com.project.boardproject.api.user.service;

import com.project.boardproject.api.user.mapper.UserMapper;
import com.project.boardproject.api.user.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/api/user")
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void insertUser(UserDTO user) {
        userMapper.insertUser(user);
    }

    public List<UserDTO> getUsers() {
        return userMapper.getUsers();
    }

    public UserDTO getUserById(UserDTO user) {
        return userMapper.getUserById(user);
    }

}
