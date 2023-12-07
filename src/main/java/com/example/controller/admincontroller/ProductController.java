package com.example.controller.admincontroller;

import com.example.dto.request.ProductDto;
import com.example.dto.response.RespCategoryDto;
import com.example.dto.response.RespProductDto;
import com.example.dto.response.RespUser;
import com.example.modle.entity.Category;
import com.example.modle.entity.Product;
import com.example.service.CategoryService;
import com.example.service.CommonService;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Controller
@RequestMapping(value = {"/admin"})
@PropertySource("classpath:config.properties")
public class ProductController {
    @Value("${path}")
    private String pathUpload;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    CommonService commonService;

    @GetMapping("/list-product")
    public String listProduct(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        List<RespProductDto> product1 = productService.findAll();
        List<RespProductDto> products = commonService.findAllProductPage(page, size);
        int totalProduct = product1.size();
        int totalPages = (int) Math.ceil((double) totalProduct / size);
        model.addAttribute("listProduct", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "admin/page/products";
    }


    @GetMapping("/add-product")
    public String addProduct(Model model) {
        ProductDto product = new ProductDto();
        model.addAttribute("listCategory", categoryService.getAll());
        model.addAttribute("product", product);
        return "admin/page/create_product";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute("product") ProductDto product,
                                @RequestParam("img_upload") MultipartFile file,
                                HttpServletRequest request) {
        // xư ly upload file lên server
        String fileName = file.getOriginalFilename();
        File fileLocal = new File(pathUpload + fileName);
        // kiểm tra thư mục, nếu chưa có thì tạo
        if (!fileLocal.exists()) {
            fileLocal.mkdir();
        }
        try {
            file.transferTo(fileLocal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        product.setImage(fileName);
        productService.create(product);
        return "redirect:/admin/list-product";
    }


    @GetMapping("/delete-product/{id}")
    public String delete(@PathVariable("id") int id) {
        productService.delete(id);
        return "redirect:/admin/list-product";
    }

    @GetMapping("/edit-product/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("listCategory", categoryService.getAll());
        model.addAttribute("productEdit", productService.findById(id));
        System.out.println(productService.findById(id));
        return "admin/page/edit-product";
    }


    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productEdit") ProductDto product,
                                @RequestParam("img_upload") MultipartFile file,
                                @PathVariable("id") Integer id,
                                HttpServletRequest request) {
        if (!file.isEmpty()) {
            // xử lý upload file
            String fileName = file.getOriginalFilename();
            File fileLocal = new File(pathUpload + fileName);

            if (!fileLocal.exists()) {
                fileLocal.mkdir();
            }

            try {
                file.transferTo(fileLocal);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            product.setImage(fileName);
        } else {
            // Nếu không có ảnh mới được cung cấp, giữ nguyên ảnh hiện tại
            RespProductDto respProductDto = productService.findById(id);
            product.setImage(respProductDto.getImage());
        }

        productService.update(product, id);
        return "redirect:/admin/list-product";
    }

}
