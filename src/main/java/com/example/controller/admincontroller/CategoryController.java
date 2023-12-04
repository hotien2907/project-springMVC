package com.example.controller.admincontroller;

import com.example.dto.request.CategoryDto;
import com.example.dto.response.RespCategoryDto;
import com.example.modle.entity.Category;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = {"/admin"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list-category")
    public String category(Model model) {
//          thêm mới
        CategoryDto category = new CategoryDto();
        model.addAttribute("category", category);
        // tra danh sách category
        List<RespCategoryDto> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/page/categories";
    }


    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        //edit category
        RespCategoryDto categoryEdit = categoryService.findById(id);
        category(model.addAttribute("categoryEdit", categoryEdit));
        return "admin/page/edit-category";
    }

    ;

    @PostMapping("/update-category")
    public String update(@RequestParam("categoryId") Integer categoryId, @ModelAttribute("category") Category category) {
        System.out.println(categoryId);
        category.setStatus(true);
        categoryService.updateById(category, categoryId);
        return "redirect:admin/list-category";
    }

    ;

    @PostMapping("/create-category")
    public String createCategory(@ModelAttribute("category") CategoryDto category) {
        Boolean check = categoryService.create(category);
        if (!check) {
            return "category/add?err=";
        }
        ;
        return "redirect:/admin/list-category";

    }

    @GetMapping("/delete-category/{id}")
    public String delete(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        return "redirect:/admin/list-category";
    }


}
