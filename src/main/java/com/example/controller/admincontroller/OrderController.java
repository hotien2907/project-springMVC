package com.example.controller.admincontroller;

import com.example.dto.request.UserLoginDto;
import com.example.dto.response.RespUser;
import com.example.modle.entity.Cart;
import com.example.modle.entity.CartItem;
import com.example.modle.entity.Order;
import com.example.service.CartItemService;
import com.example.service.CartService;
import com.example.service.OderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Controller

public class OrderController {
    @Autowired
    OderService oderService;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    HttpSession httpSession ;

    @PostMapping("/create-order")
    public String createOrder(@ModelAttribute("userInfo") Order userInfo) {
        RespUser userLogin = (RespUser) httpSession.getAttribute("user");
        Cart cart = cartService.findByIdUser(userLogin.getUserId());
        userInfo.setUserId(userLogin.getUserId());
        userInfo.setTotal(total());
        System.out.println(userInfo);
      oderService.createNewOrder(userInfo);

        oderService.clearCart(cart.getCartId());
        return "redirect:/home";
    }


    @GetMapping("/check-out")
    public String checkOut(Model model){
        Order userInfo = new Order();
        List<CartItem> cartItems =  cartItemService.findAllByIdCart();
         Double total = total();
        model.addAttribute("total", total) ;
        model.addAttribute("carts", cartItems) ;
         model.addAttribute("userInfo",userInfo);






        return "user/page/checkout";
    }

    @GetMapping("/admin/list-order")
    public String listOrder(Model model){
   List<Order> orders = oderService.findAllOrder();
    model.addAttribute("orders",orders);

        return "admin/page/orders";
    }

    public Double total(){
        List<CartItem> cartItems =  cartItemService.findAllByIdCart();
        double total = 0;
        if(cartItems !=null){
            for ( CartItem ca : cartItems) {
                total += ca.getQuantity() * ca.getProduct().getPrice();
            }
        }
        return total;
    }
}
