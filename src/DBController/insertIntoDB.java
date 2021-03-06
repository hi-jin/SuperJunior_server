package DBController;

import java.sql.Statement;


// DB에 정보를 넣는 클래스이다.
public class insertIntoDB {
	
	// clients 테이블에 사용자 정보를 넣는다.
	public static boolean addClient(String ID) {
		try {
			Statement stmt = connectToDB.DBStmt();
			String 		sql = "Insert into clients (userid) values (\"" + ID + "\")";
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	// todo_list 테이블에 매니저 정보를 넣는다.
	public static boolean addTeam(String ID) {
		try {
			Statement stmt = connectToDB.DBStmt();
			String 		sql = "Insert into team (teamid) values (\"" + ID +"\")";
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
}
