package com.example.modle.dao;
import com.example.dto.response.RespUser;
import com.example.modle.entity.User;
import com.example.util.ConnectionDB;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {



    @Override
    public User login(String email, String password) {
        User user = new User();
        Connection connection = null;
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call PROC_FIND_USER_BY_EMAIL(?)}");
            callableStatement.setString(1, email);
            boolean hasResult = callableStatement.execute();

            if (hasResult) {
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserId(resultSet.getInt("userId"));
                    user.setFullName(resultSet.getString("fullName"));
                    user.setPassword(resultSet.getString("password"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setRole(resultSet.getByte("role"));
                    user.setStatus(resultSet.getBoolean("status"));
                    user.setEmail(resultSet.getString("email"));

                    // Kiểm tra mật khẩu chỉ khi người dùng được tìm thấy
                    if (BCrypt.checkpw(password, user.getPassword())) {
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }

        return null;
    }


    @Override
    public Boolean register(User user) {
        Connection connection = null;
        // mở kết nối
        connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_ADD_USER(?,?,?,?,?)}");
            callableStatement.setString(1, user.getUserName());
            callableStatement.setString(2, user.getFullName());
            callableStatement.setString(3, user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
            callableStatement.setString(4, hashedPassword);
            callableStatement.setString(5, user.getPhone());
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
    public List<User> findAllUser() {

        Connection connection = null;
        List<User> list = new ArrayList<>();

        connection = ConnectionDB.openConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_USER()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("userName"));
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getByte("role"));
                user.setStatus(rs.getBoolean("status"));
                user.setEmail(rs.getString("email"));
                list.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }



    @Override
    public void lockUser(int id) {
        Connection conn = null;
        try {
            conn = ConnectionDB.openConnection();
            CallableStatement callableStatement = conn.prepareCall("{CALL PROC_TOGGLE_USER(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }



    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public List<String> checkDuplicateEmail() {
        List<String> emails = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionDB.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CHECK_DUPLICATE_EMAIL()}");
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                emails.add(email);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error checking duplicate email", e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return emails;
    }
    @Override
    public List<User> findAllUserPage(int startNumber, int size) {
        Connection connection = null;
        List<User> list = new ArrayList<>();

        connection = ConnectionDB.openConnection();

        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_USER_BY_PAGE(?,?)}");
            callableStatement.setInt(1, startNumber);
            callableStatement.setInt(2, size);

            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("userName"));
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getByte("role"));
                user.setStatus(rs.getBoolean("status"));
                user.setEmail(rs.getString("email"));
                list.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return list;
    }


}
