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
public class ProductDaoImpl implements  ProductDao{
    @Autowired
    CategoryDao categoryDao;
    @Override
    public List<Product> findAll(){
        Connection connection = null;
        List<Product> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_PRODUCT()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
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
    };
    @Override
    public Boolean create(Product product) {
        Connection connection = null;
        // mở kết nối
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_INSERT_PRODUCT(?,?,?,?,?,?)}");
            callableStatement.setString(1, product.getProductName());
            callableStatement.setString(2, product.getDescription());
            callableStatement.setDouble(3, product.getPrice());
            callableStatement.setString(4,product.getImage());
            callableStatement.setInt(5,product.getCategory().getCategoryId());
            callableStatement.setInt(6,product.getStock());

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
    public Boolean update(Product product, Integer id) {
        Connection conn = null;
        conn = ConnectionDB.openConnection();

        try {

            CallableStatement callableStatement = conn.prepareCall("{CALL PROC_UPDATE_PRODUCT_BY_ID(?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setString(2, product.getProductName());
            callableStatement.setString(3, product.getDescription());
            callableStatement.setDouble(4, product.getPrice());
            callableStatement.setString(5,product.getImage());
            callableStatement.setInt(6,product.getCategory().getCategoryId());
            callableStatement.setInt(7,product.getStock());
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
    public Product findById(Integer id) {

        Product product = null;
        Connection conn = null;
        try {
            // mỏ kết nối
            conn = ConnectionDB.openConnection();
            // chuẩn bị câu lệnh
            CallableStatement callableStatement = conn.prepareCall("{CALL PRODUCT_BY_ID(?)}");
            // truyền đối số
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                product = new Product();

                product.setProductName(rs.getString("productName"));
                product.setProductId(rs.getInt("productId"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setStock(rs.getInt("stock"));
                Category category = categoryDao.findById(rs.getInt("categoryId"));
                product.setCategory(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return product;
    }

    @Override
    public void delete(Integer id) {
        Connection connection = null;
        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_DELETE_PRODUCT_BY_ID(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }


    @Override
    public List<Product> getProductFeatured() {
        Connection connection = null;
        List<Product> list = new ArrayList<>();
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_PRODUCT_FEATURED()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
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
