package com.example.controller.usercotroller;

import com.example.dto.request.UserLoginDto;
import com.example.dto.request.UserRegisterDto;
import com.example.dto.response.RespUser;
import com.example.modle.entity.Cart;
import com.example.service.CartService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
     CartService cartService;
    @GetMapping("/register")
    public String register(Model model) {
       UserRegisterDto user = new UserRegisterDto();
        model.addAttribute("user", user);
        return "user/page/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegisterDto user, BindingResult result) {
        List<String> duplicateEmails = userService.checkDuplicateEmail();
        user.CheckValidate(result, duplicateEmails);

        if (!result.hasErrors()) {
            if (userService.register(user)) {
                return "user/page/login";
            }
        }
        return "user/page/register";
    }


    @GetMapping("/login")
    public String login( @CookieValue(required = false, name = "email") String emailCookie, Model model) {
        UserLoginDto user = new UserLoginDto();
        // check cuc co khoz
        if (emailCookie != null) {
            user.setEmail(emailCookie);
            model.addAttribute("checked", true);
        }
        model.addAttribute("user", user);
        return "user/page/login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid @ModelAttribute("user") UserLoginDto user,
                            BindingResult result,
                            HttpSession httpSession,
                            @RequestParam(required = false, name = "checked") Boolean isCheck,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes
                           ) {

        // Kiểm tra lỗi ngay từ đầu
        if (result.hasErrors()) {
            // Nếu có lỗi, chuyển hướng người dùng về trang đăng nhập
            return "user/page/login";
        }
        System.out.println(isCheck);
        // Tiếp tục với logic xử lý đăng nhập

        if (isCheck != null && isCheck) {
            // Code cho việc xử lý khi chọn "Remember me"
            Cookie cookieEmail = new Cookie("email", user.getEmail());
            cookieEmail.setMaxAge(24 * 60 * 60);
            response.addCookie(cookieEmail);
        } else {
            // Code cho việc xử lý khi không chọn "Remember me"
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("email".equals(cookie.getName())) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
        }

        // Nếu đăng nhập thành công, chuyển hướng người dùng về trang chính
        RespUser userLogin = userService.login(user.getEmail(), user.getPassword());
        if (userLogin != null) {
            if(userLogin.getStatus()){
                httpSession.setAttribute("user", userLogin);

//                redirectAttributes.addAttribute("success", "Đăng Nhập Thành công.");
                return "redirect:/home";
            }else {
                redirectAttributes.addAttribute("error", "Tài khoản của bạn đã bị khóa.");
                return "redirect:/login";
            }

        }
            // Nếu đăng nhập thất bại, chuyển hướng với thông báo lỗi
            redirectAttributes.addAttribute("error", "Thông tin đăng nhập không khớp.");
            return "redirect:/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    }




