package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

abstract class UpdateJdbcTemplate {

    void update(User user) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForUpdate();
            pstmt = con.prepareStatement(sql);
            setValuesForUpdate(user, pstmt);
            
            pstmt.executeUpdate();
        }finally {
            if(pstmt != null) {
                pstmt.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
    
    abstract void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException;
    abstract String createQueryForUpdate();
}
