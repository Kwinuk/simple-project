package com.project.boardproject.api.user.controller;

import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Controller
public class UserController {
    @Autowired
    UserService userService;

    /**
     * @return
     */
    @GetMapping("/index")
    public String register() {
        return "index";
    }

    /**
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String insertUser(UserDTO user, Model model) {
        String txt = "";
        try {
            txt = "********** insertUser SUCCESS **********";
            System.out.println(txt);
            userService.insertUser(user);
            model.addAttribute("userlist", userService.getUsers());
            return "userlist";
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
    public String getUsers(Model model) {
        model.addAttribute("userlist", userService.getUsers());
        return "userlist";
    }
    @GetMapping("/login")
    public String getUserById(UserDTO user, Model model) {
        model.addAttribute(userService.getUserById(user));
        return "userlist";
    }
}
