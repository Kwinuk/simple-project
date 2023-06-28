package com.project.boardproject.api.user.controller;

import com.project.boardproject.api.user.model.UserDTO;
import com.project.boardproject.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("registerPage")
    public String registerPage(){
        return "register";
    }
    /**
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(UserDTO user) {
        try {
            userService.register(user);
            return "index";
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
        model.addAttribute("users", userService.getUsers());
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(UserDTO user, Model model) {
        model.addAttribute("userInfo",userService.login(user));
        System.out.printf("userName" + model.getAttribute(user.getUserName()));
        return "index";
    }
}
