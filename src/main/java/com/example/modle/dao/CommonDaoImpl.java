package com.example.modle.dao;

import com.example.modle.entity.Category;
import com.example.modle.entity.Product;
import com.example.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CommonDaoImpl implements CommonDao {
    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Product> productByCategory(Integer categoryId) {

        Connection connection = null;
        List<Product> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PRODUCT_BY_ID_CATEGORY(?)}");
            callableStatement.setInt(1, categoryId);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                Category category = categoryDao.findById(rs.getInt("categoryId"));
                product.setCategory(category);
                list.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<Product> searchProduct(String keyName) {
        Connection connection = null;
        List<Product> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SEARCH_PRODUCT(?)}");
            callableStatement.setString(1, keyName);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                Category category = categoryDao.findById(rs.getInt("categoryId"));
                product.setCategory(category);
                list.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<Product> sortProduct(int value) {
        Connection connection = null;
        List<Product> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SORT_PRODUCTS(?)}");
            callableStatement.setInt(1, value);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                Category category = categoryDao.findById(rs.getInt("categoryId"));
                product.setCategory(category);
                list.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }

    @Override
    public List<Product> productRelated(int idCategory, int idProduct) {
        Connection connection = null;
        List<Product> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_RELATED_PRODUCTS(?,?)}");
            callableStatement.setInt(1,idCategory);
            callableStatement.setInt(2,idProduct);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                Category category = categoryDao.findById(rs.getInt("categoryId"));
                product.setCategory(category);
                list.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }


}
