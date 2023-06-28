package com.project.boardproject.api.user.mapper;

import com.project.boardproject.api.user.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(UserDTO user);

    List<UserDTO> getUsers();

    UserDTO getUserById(UserDTO user);
}
