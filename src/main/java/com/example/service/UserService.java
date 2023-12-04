package com.example.service;

import com.example.dto.request.UserRegisterDto;
import com.example.dto.response.RespUser;
import com.example.modle.entity.User;

import java.util.List;

public interface UserService {
    RespUser login(String email, String password);
    Boolean register (UserRegisterDto userRegisterDto);
   List <RespUser> findAllUser();
    void lockUser(int id);
    User findByEmail(String email);
    List <String>  checkDuplicateEmail();
    RespUser loginAdmin(String email,String password);
    List<RespUser> findAllUserPage(int page, int size);

}
