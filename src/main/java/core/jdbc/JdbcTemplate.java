package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmtSetter.setValues(pstmt).executeQuery();){
            
            List<T> list = new ArrayList<>();
            while(rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
          
            return list;
        }catch(SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object...values){
        return query(sql, rowMapper, createPreparedStatementSetter(values));
    }


    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter){
        List<T> list = query(sql, rowMapper, pstmtSetter);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object...values){
        return queryForObject(sql, rowMapper, createPreparedStatementSetter(values));
    }
    
    public void update(String sql, PreparedStatementSetter pstmtSetter){
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);){
            
            pstmtSetter.setValues(pstmt).executeUpdate();
        }catch(SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
    public void update(String sql, Object...values){
        update(sql, createPreparedStatementSetter(values));
    }
    
    private PreparedStatementSetter createPreparedStatementSetter(Object... values){
        return new PreparedStatementSetter() {
            @Override
            public PreparedStatement setValues(PreparedStatement pstmt) throws SQLException {
                for(int i = 0; i < values.length; i++) {
                    pstmt.setObject(i+1, values[i]);
                }
                return pstmt;
            }
        };
    }
    
}
