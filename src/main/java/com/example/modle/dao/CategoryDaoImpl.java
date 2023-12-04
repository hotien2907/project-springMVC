package com.example.modle.dao;
import com.example.modle.entity.Category;
import com.example.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryDaoImpl implements CategoryDao{
    @Override
    public List<Category> findAll() {
        Connection connection = null;
       List<Category> list = new ArrayList<>();

        connection = ConnectionDB.openConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_CATEGORY()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setStatus(rs.getBoolean("status"));
                list.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }

    @Override
    public Boolean create(Category category) {
        Connection connection = null;
        // mở kết nối
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_INSERT_CATEGORY(?,?)}");
            callableStatement.setString(1, category.getCategoryName());
            callableStatement.setBoolean(2, category.getStatus());

            // thực thi
            int check = callableStatement.executeUpdate();
            if (check > 0) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Boolean update(Category category, Integer id) {

        Connection conn = null;
        conn = ConnectionDB.openConnection();

        try {

            CallableStatement callableStatement = conn.prepareCall("{CALL PROC_UPDATE_CATEGORY_BY_ID(?,?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setString(2, category.getCategoryName());
            callableStatement.setBoolean(3, category.getStatus());

            // thực thi
            callableStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return false;
    }

    @Override
    public Category findById(Integer id) {
        Category category = null;
        Connection conn = null;
        try {
            // mỏ kết nối
            conn = ConnectionDB.openConnection();
            // chuẩn bị câu lệnh
            CallableStatement callableStatement = conn.prepareCall("{CALL CATEGORY_BY_ID(?)}");
            // truyền đối số
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setStatus(rs.getBoolean("status"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return category;
    }

    @Override
    public void delete(Integer categoryId) {
        Connection connection = null;
        try {
            connection = ConnectionDB.openConnection();
//            CallableStatement callableStatement1 = connection.prepareCall("{CALL PROC_DELETE_PRODUCT_BY_CATEGORY(?)}");
//            callableStatement1.setInt(1, categoryId);
//            callableStatement1.executeUpdate();

            CallableStatement callableStatement2 = connection.prepareCall("{CALL PROC_DELETE_CATEGORY_BY_ID(?)}");
            callableStatement2.setInt(1, categoryId);
            callableStatement2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }


}
