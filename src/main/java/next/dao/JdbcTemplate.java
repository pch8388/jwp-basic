package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public class JdbcTemplate {
    
    <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmtSetter.setValues(pstmt).executeQuery();){
            
            List<T> list = new ArrayList<>();
            while(rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
          
            return list;
        }catch(SQLException e) {
            throw new DataAccessException();
        }
    }
    
    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object...values){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);){
            
            int idx = 0;
            for(Object obj : values) {
                pstmt.setObject(++idx, obj);
            }
            ResultSet rs = pstmt.executeQuery();
            
            List<T> list = new ArrayList<>();
            if(rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
          
            return list;
        }catch(SQLException e) {
            throw new DataAccessException();
        }
    }
    
    <T> T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmtSetter.setValues(pstmt).executeQuery();){

            if(rs.next()) {
                return rowMapper.mapRow(rs);
            }
            return null;
        }catch(SQLException e) {
            throw new DataAccessException();
        }
    }

    <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object...values){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);){
            
            int idx = 0;
            for(Object obj : values) {
                pstmt.setObject(++idx, obj);
            }
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return rowMapper.mapRow(rs);
            }
            return null;
        }catch(SQLException e) {
            throw new DataAccessException();
        }
    }
    
    void update(String sql, PreparedStatementSetter pstmtSetter){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);){
            
            pstmtSetter.setValues(pstmt).executeUpdate();
        }catch(SQLException e) {
            throw new DataAccessException();
        }
    }
    
    void update(String sql, Object...values){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);){
            
            int idx = 0;
            for(Object obj : values) {
                pstmt.setObject(++idx, obj);
            }
            
            pstmt.executeUpdate();
        }catch(SQLException e) {
            throw new DataAccessException();
        }
    }
}
