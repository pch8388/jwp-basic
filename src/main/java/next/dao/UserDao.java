package next.dao;

import java.sql.ResultSet;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
    public void insert(User user){
        new JdbcTemplate() {}.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", (pstmt) -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            return pstmt;
        });
    }


    public void update(User user){
        new JdbcTemplate() {}.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?", (pstmt) -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
            return pstmt;
        });
    }


    public List<User> findAll(){
        return new JdbcTemplate() {}.query("SELECT userId, password, name, email FROM USERS", (rs) -> {
            return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
        });
        
    }

    public User findByUserId(String userId){
        return new JdbcTemplate() {}.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", (ResultSet rs) -> {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
               }, userId);
    }
}
