package com.example.controller.commoncontroller;
import com.example.dto.response.RespProductDto;
import com.example.service.CategoryService;
import com.example.service.CommonService;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = {"/", "/home"})
public class CommonController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CommonService commonService;
    @Autowired
    ProductService productService;
    @GetMapping("/menu-category")
    public String menuCategory(Model model,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int size) {
        List<RespProductDto> product1 = productService.findAll();
        model.addAttribute("listCategory", categoryService.getAll());
        List<RespProductDto> products = commonService.findAllProductPage(page, size);
        int totalProduct = product1.size();
        int totalPages = (int) Math.ceil((double) totalProduct / size);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "user/page/category";
    }


    @GetMapping("/product-category/{id}")
    public String productFindCategory(Model model, @PathVariable("id") int id) {
        model.addAttribute("listCategory", categoryService.getAll());
        model.addAttribute("products", commonService.productByCategory(id));
        return "user/page/category";
    }
    @GetMapping("/search-product")
    public String searchProduct(@RequestParam(name = "search", required = false) String searchKey, Model model) {
        model.addAttribute("products", commonService.searchProduct(searchKey));
        return "user/page/index";
    }

        @GetMapping("/sort-product")
        public String sortProduct(@RequestParam(name = "value", required = false) int value, Model model) {
            model.addAttribute("listCategory", categoryService.getAll());
            model.addAttribute("products", commonService.sortProduct(value));
            return "user/page/category";
        }
}
