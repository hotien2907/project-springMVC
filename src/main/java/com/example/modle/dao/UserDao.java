package com.example.modle.dao;

import com.example.modle.entity.User;

import java.util.List;

public interface UserDao {
  User login(String email, String password);
  Boolean   register (User user);
 List <User> findAllUser();
  void lockUser(int id);
 User findByEmail(String email);
 List<String>  checkDuplicateEmail();
 List<User>findAllUserPage (int page, int size);

}
