package com.example.controller.admincontroller;

import com.example.dto.request.UserLoginDto;
import com.example.dto.response.RespUser;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller

public class loginAdminController {

    @Autowired
    private UserService userService;
    @GetMapping("/login-admin")
    public String loginAdmin(Model model){
        UserLoginDto user = new UserLoginDto();
        model.addAttribute("user",user);
        return "admin/page/login";
    }

    @PostMapping("/login-admin")
    public String postLoginAdmin(@ModelAttribute("user") UserLoginDto user,
                            HttpSession httpSession){
        RespUser userLogin = userService.loginAdmin(user.getEmail(),user.getPassword());

        if(userLogin != null){
            httpSession.setAttribute("admin",userLogin);
            return "redirect:/admin";
        }
        return "redirect:/login-admin";
    }
    @GetMapping("/logout-admin")
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/login-admin";
    }
}
