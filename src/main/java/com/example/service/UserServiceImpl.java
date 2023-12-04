package com.example.service;

import com.example.dto.request.UserRegisterDto;
import com.example.dto.response.RespProductDto;
import com.example.dto.response.RespUser;
import com.example.modle.dao.CartDao;
import com.example.modle.dao.UserDao;
import com.example.modle.entity.Cart;
import com.example.modle.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    UserDao userDao;
    @Autowired
    CartDao cartDao;


    @Override
    public RespUser login(String email, String password) {

        User user = userDao.login(email, password);

        if (user != null) {
         Cart cart =  cartDao.findByIdUser(user.getUserId());
            if(cart==null){
             Cart   cartItem = new Cart() ;
                cartItem.setUserId(user.getUserId());
                cartDao.addCart(cartItem) ;
            }

            return modelMapper.map(user, RespUser.class);
        } else {
            return null;
        }
    }


    @Override
    public Boolean register(UserRegisterDto userRegisterDto) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRegisterDto, User.class);
        return userDao.register(user);
    }

    ;

    @Override
    public List<RespUser> findAllUser() {
        List<User> users = userDao.findAllUser();
        ModelMapper modelMapper = new ModelMapper();
        return users.stream()
                .map(user -> modelMapper.map(user, RespUser.class))
                .collect(Collectors.toList());
    }

    @Override
    public void lockUser(int id) {
        userDao.lockUser(id);
    }


    @Override
    public User findByEmail(String email) {

        return null;
    }

    public List<String> checkDuplicateEmail() {
        return userDao.checkDuplicateEmail();
    }

    @Override
    public RespUser loginAdmin(String email, String password) {
        RespUser user = login(email,password);
        if(user.getRole() == 1){
            return user;
        }
        return null;
    }


    @Override
    public List<RespUser> findAllUserPage(int page, int size) {
        List<User> users = userDao.findAllUserPage(page, size);
        return users.stream()
                .map(user -> modelMapper.map(user, RespUser.class))
                .collect(Collectors.toList());
    }


}
