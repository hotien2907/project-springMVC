package com.example.controller.usercotroller;


import com.example.dto.response.RespProductDto;
import com.example.modle.entity.CartItem;
import com.example.modle.entity.Product;
import com.example.service.CartItemService;
import com.example.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService ;
    @Autowired
    HttpSession httpSession ;

    // create
    @GetMapping("")
    public String cart(Model model){
        List<CartItem> carts =  cartItemService.findAllByIdCart() ;
        double total = 0;
        if(carts !=null){
            for ( CartItem ca : carts) {
                total += ca.getQuantity() * ca.getProduct().getPrice();
            }
        }
        model.addAttribute("total", total) ;

        model.addAttribute("carts", carts) ;
        return "user/page/cart";
    }


    @PostMapping("")
    public String postCart(@RequestParam("productId") Integer productId,
                           @RequestParam("quantity") Integer quantity ) {
        ModelMapper modelMapper = new ModelMapper() ;
        RespProductDto productDto = productService.findById(productId) ;
        CartItem existingCartItem = cartItemService.findById(productId) ;
        if(existingCartItem !=null){
            int newQty = existingCartItem.getQuantity()+ quantity ;
            System.out.println(newQty);
            int idCartItem = existingCartItem.getId();
            System.out.println(idCartItem);
            cartItemService.updateQty(newQty,idCartItem);
        }else {
                CartItem cartItem = new CartItem() ;
                cartItem.setProduct(modelMapper.map(productDto, Product.class));
                cartItem.setQuantity(quantity);
                cartItemService.create(cartItem) ;
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart-home/{productId}")
    public String postCartHome(@PathVariable("productId") Integer productId,
                               @RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {
        ModelMapper modelMapper = new ModelMapper();
        RespProductDto productDto = productService.findById(productId);
        CartItem existingCartItem = cartItemService.findById(productId);

        if (existingCartItem != null) {
            int newQty = existingCartItem.getQuantity() + quantity;
            System.out.println(newQty);
            int idCartItem = existingCartItem.getId();
            System.out.println(idCartItem);
            cartItemService.updateQty(newQty, idCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(modelMapper.map(productDto, Product.class));
            cartItem.setQuantity(quantity);
            cartItemService.create(cartItem);
        }

        return "redirect:/cart";
    }



    @GetMapping("/delete-cart/{id}")
    public String deleteCart(@PathVariable Integer id ) {
        Boolean isDelete = cartItemService.delete(id);
        if(isDelete){
            return "redirect:/cart";
        }
        return "user/page/cart";
    }

    @PostMapping("/update-cart")
    public String updateCart(@RequestParam("quantity") Integer quantity, @RequestParam("id") Integer id) {
        Boolean isUpdate = cartItemService.updateQty(quantity, id);
        if (isUpdate) {
            return "redirect:/cart";
        }
        return "user/page/cart";
    }

}
