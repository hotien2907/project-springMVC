package com.example.modle.dao;

import com.example.dto.response.RespUser;
import com.example.modle.entity.Cart;
import com.example.modle.entity.CartItem;
import com.example.modle.entity.Product;
import com.example.util.ConnectionDB;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CartItemDaoImpl implements  CartItemDao {
    @Autowired
    ProductDao productDao;
    @Autowired
    CartDao cartDao;

    @Autowired
    private HttpSession httpSession;
    @Override
    public List<CartItem> findAll(Integer idCart) {

        Connection connection = null;
        List<CartItem> list = new ArrayList<>();
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_CART_ITEM_BY_CART(?)}");
            callableStatement.setInt(1, idCart);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                Product product = productDao.findById(rs.getInt("productId"));
                cartItem.setProduct(product) ;
                cartItem.setQuantity(rs.getInt("quantity"));

                RespUser userLogin = (RespUser) httpSession.getAttribute("user");
                Cart cart = cartDao.findByIdUser(userLogin.getUserId());
                cartItem.setCart(cart);
                list.add(cartItem) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list ;

    }

    @Override
    public CartItem findById(Integer id) {
        Connection connection = null;
        CartItem cartItem = null; // Initialize cartItem to null
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_CART_ITEM_BY_ID_PRODUCT(?)}");
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                Product product = productDao.findById(rs.getInt("productId"));
                cartItem.setProduct(product);
                cartItem.setQuantity(rs.getInt("quantity"));
                RespUser userLogin = (RespUser) httpSession.getAttribute("user");
                Cart cart = cartDao.findByIdUser(userLogin.getUserId());
                cartItem.setCart(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return cartItem;
    }


    @Override
    public Boolean create(CartItem item) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CREATE_CART_ITEM(?,?,?)}");
            callableStatement.setInt(1, item.getProduct().getProductId());
            callableStatement.setInt(2, item.getQuantity());
            callableStatement.setInt(3,item.getCart().getCartId());
            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return isCheck;
    }

    @Override
    public Boolean updateQty(Integer qty, Integer idCart) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_UPDATE_QUANTITY_CART_ITEM(?,?)}");
            callableStatement.setInt(1, qty );
            callableStatement.setInt(2,idCart);
            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return isCheck;
    }

    @Override
    public Boolean delete(Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_DELETE_CART_ITEM(?)}");
            callableStatement.setInt(1,id);
            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeConnection(connection);
        }
        return isCheck;
    }
    }
