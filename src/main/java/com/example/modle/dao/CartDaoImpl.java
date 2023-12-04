package com.example.modle.dao;


import com.example.modle.entity.Cart;
import com.example.modle.entity.User;
import com.example.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CartDaoImpl implements  CartDao {

    @Autowired
    private UserDao userDao;
    @Override
    public Boolean addCart(Cart cart) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_CART(?)}");
            callableStatement.setInt(1, cart.getUserId());
            int check = callableStatement.executeUpdate();
            if (check > 0) {
                isCheck = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return isCheck;

    }

    @Override
    public Cart findByIdUser(Integer idUser) {

        Connection connection = null;
        Cart cart = new Cart() ;
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_CART_BY_ID(?)}");
            callableStatement.setInt(1,idUser);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                cart.setCartId(rs.getInt("cartId"));
                cart.setUserId(rs.getInt("userId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return cart;
    }

    @Override
    public boolean clearCart(int cartId) {
        Connection conn = null;
        try {
            conn= ConnectionDB.openConnection();
            CallableStatement callSt = conn.prepareCall("{call PROC_ClearCartDetail(?)}");
            callSt.setInt(1,cartId);
            callSt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                ConnectionDB.closeConnection(conn);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
