package DBController;

import java.sql.Statement;

// DB에 정보를 넣는 클래스이다.
public class insertIntoDB {
	
	// clients 테이블에 사용자 정보를 넣는다.
	public static boolean addClient(String ID, String Pass) {
		try {
			Statement 	stmt = connectToDB.DBStmt();
			String 		sql = "Insert into clients (userid, pwd) values (\"" + ID + "\",\"" + Pass +  "\")";
			stmt.executeUpdate(sql);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	// todo_list 테이블에 매니저 정보를 넣는다.
	public static boolean addTodolist(String ID, String mandSchedule, String chalSchedule) {
		try {
			Statement 	stmt = connectToDB.DBStmt();
			String 		sql = "Insert into todo_list (userid, mand_schedule, chal_schedule) values (\"" + ID + "\",\"" + mandSchedule + "\",\"" + chalSchedule +"\")";
			stmt.executeUpdate(sql);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
