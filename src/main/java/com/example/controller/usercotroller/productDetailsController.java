package com.example.controller.usercotroller;
import com.example.dto.request.CategoryDto;
import com.example.dto.request.ProductDto;
import com.example.dto.response.RespCategoryDto;
import com.example.dto.response.RespProductDto;
import com.example.service.CategoryService;
import com.example.service.CommonService;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class productDetailsController {
    @Autowired
    private ProductService productService;
    @Autowired
    CommonService commonService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/product-details/{id}")
    public String productDetails(Model model,@PathVariable("id") Integer id){
        int categoryId = 0;
        RespProductDto productDto = productService.findById(id);
        List<RespCategoryDto> categoryDto =  categoryService.findAll();
        for (RespCategoryDto ca :categoryDto
                ) {
            if(ca.getCategoryName().equals(productDto.getCategoryName())){
                categoryId =ca.getCategoryId();
                break;
            }
        }
        List<RespProductDto> productRelated = commonService.productRelated(categoryId,id);

        model.addAttribute("listProduct",productRelated);
        model.addAttribute("product",productDto);
        return "user/page/product-details";
    }
}
