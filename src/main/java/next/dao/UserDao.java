package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }
        }.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }


    public void update(User user) throws SQLException {
        new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                // TODO Auto-generated method stub
                return null;
            }
        }.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?");
    }


    @SuppressWarnings("unchecked")
    public List<User> findAll() throws SQLException {
        return new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) {
                
            }
            
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        }.query("SELECT userId, password, name, email FROM USERS");
        
    }

    public User findByUserId(String userId) throws SQLException {
        return (User) new JdbcTemplate() {
            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
            
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        }.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?");
    }
}
