package com.example.modle.dao;
import com.example.modle.entity.Category;
import com.example.modle.entity.Order;
import com.example.modle.entity.Product;
import com.example.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao{

    @Override
    public void clearCart(int cartId) {
        Connection conn = null;

        try {
            conn= ConnectionDB.openConnection();
            CallableStatement callSt = conn.prepareCall("{call PROC_CLEAR_CART_ITEM(?)}");
            callSt.setInt(1,cartId);
            callSt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                ConnectionDB.closeConnection(conn);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createNewOrder(Order o) {
        Connection conn = null;
        try {
            conn = ConnectionDB.openConnection();
            CallableStatement callSt = conn.prepareCall("{CALL PROC_CREATE_NEW_ORDER(?,?,?,?,?,?)}");
            callSt.setInt(1, o.getUserId());
            callSt.setString(2, o.getRecipientName());
            callSt.setDouble(3, o.getTotal());
            callSt.setInt(4, o.getStatus());
            callSt.setString(5, o.getAddress());
            callSt.setString(6, o.getPhone());

            int result = callSt.executeUpdate();
            if (result > 0) {
                System.out.println("Đã tạo đơn hàng thành công.");
            } else {
                System.out.println("Không thể tạo đơn hàng.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    @Override
    public List<Order> findAllOrder() {
        Connection connection = null;
        List<Order> orderList = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_ORDER()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setUserId(rs.getInt("userId"));
                order.setRecipientName(rs.getString("recipientName"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(rs.getInt("status"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                Timestamp sqlTimestamp = rs.getTimestamp("createdDate");
                LocalDateTime createDate = sqlTimestamp.toLocalDateTime();

                // Chuyển đổi từ LocalDateTime sang LocalDate
                LocalDate createLocalDate = createDate.toLocalDate();
                order.setCreateDate(createLocalDate);

                orderList.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return orderList;
    }

}





