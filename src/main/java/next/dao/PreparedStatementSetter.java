package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
    PreparedStatement setValues(PreparedStatement pstmt) throws SQLException;
}
