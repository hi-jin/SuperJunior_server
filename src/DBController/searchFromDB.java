package DBController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// DB에 있는 정보를 가져오기 위한 클래스이다.
public class searchFromDB {
	
	// 테이블 이름에 맞는 정보를 ResultSet으로 반환한다.
	public synchronized static ResultSet searchObjects(String tableName) {
        Statement stmt = connectToDB.DBStmt();
        ResultSet rs = null;
        String sql; 
        try {
	        sql= "SELECT * from " + tableName;
	        rs = stmt.executeQuery(sql);
	        
			return rs;
        } catch(SQLException e) {
        	return null;
        }
	}
	
}
