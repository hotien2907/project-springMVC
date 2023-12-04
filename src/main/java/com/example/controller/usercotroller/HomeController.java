package com.example.controller.usercotroller;

import com.example.dto.response.RespProductDto;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
    @RequestMapping(value = {"/", "/home"})
    public class HomeController {
    @Autowired
    private ProductService productService;
        @RequestMapping("")
        public String home(Model model) {
            List<RespProductDto> products = productService.findAll();
            model.addAttribute("products", products);

            List<RespProductDto> productFeatured = productService.getProductFeatured();
            model.addAttribute("productFeatured", productFeatured);
            return "user/page/index";
        }
    };

