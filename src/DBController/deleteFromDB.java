package DBController;

import java.sql.Statement;

// DB에서 정보를 삭제하기 위한 클래스이다.
public class deleteFromDB {
	
	// clients 테이블에서 해당 ID를 삭제한다.
	public static boolean deleteClient(String ID) {
		try {
			Statement stmt = connectToDB.DBStmt();
	        String sql = "delete from clients where userid=\""+ID+"\"";
	        stmt.executeUpdate(sql);
	        
			return true;
		}catch(Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}
	
	// team 테이블에서 해당 ID를 삭제한다.
	public static boolean deleteTeam(String ID) {
		try {
			Statement stmt = connectToDB.DBStmt();
			String sql = "delete from team where userid=\""+ID+"\"";
			stmt.executeUpdate(sql);
	        stmt.close();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
