package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public abstract class JdbcTemplate {
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    List query(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmtSetter.setValues(pstmt).executeQuery();
            
            List list = new ArrayList<>();
            if(rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
          
            return list;
        }finally {
            if(rs != null) {
                rs.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
    
    Object queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmtSetter.setValues(pstmt).executeQuery();

            if(rs.next()) {
                return rowMapper.mapRow(rs);
            }
            return null;
        }finally {
            if(rs != null) {
                rs.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
    
    void update(String sql, PreparedStatementSetter pstmtSetter) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            
            pstmtSetter.setValues(pstmt).executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
}
