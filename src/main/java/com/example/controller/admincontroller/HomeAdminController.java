package com.example.controller.admincontroller;

import com.example.dto.response.RespCategoryDto;
import com.example.dto.response.RespProductDto;
import com.example.dto.response.RespUser;
import com.example.modle.entity.Order;
import com.example.service.CategoryService;
import com.example.service.OderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = {"/admin"})
public class HomeAdminController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    OderService oderService;
    @GetMapping("")
    public String home(Model model) {
        // tra danh sách category
        List<RespCategoryDto> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        List<RespProductDto> products = productService.findAll();
        model.addAttribute("products", products);

        List<RespUser> users = userService.findAllUser();
        model.addAttribute("users", users);

        List<Order> orders = oderService.findAllOrder();
        model.addAttribute("orders",orders);
        return "admin/page/dashboard";

    }

    @GetMapping("/add-user")
    public String addUser(){
        return "admin/page/create-user";
    }


};
