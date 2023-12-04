package com.example.controller.admincontroller;

import com.example.dto.response.RespUser;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = {"/admin"})
public class UserAdminController {
    @Autowired
    UserService userService;

    @GetMapping("/list-user")
    public String listUser(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "2") int size) {
        List<RespUser> user1 = userService.findAllUser();
        List<RespUser> users = userService.findAllUserPage(page, size);
        int totalUsers = user1.size();
        System.out.println(totalUsers);
        int totalPages = (int) Math.ceil((double) totalUsers / size);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin/page/user";
    }


    @GetMapping("/toggle-user/{id}")
    public String toggleUser(@PathVariable("id") int id) {
        userService.lockUser(id);
        return "redirect:/admin/list-user";
    }
















}
















