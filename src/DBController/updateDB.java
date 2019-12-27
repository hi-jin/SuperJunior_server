package DBController;

import java.sql.SQLException;
import java.sql.Statement;

public class updateDB {
	/*
	 * updateTarget 배열에 업데이트할 칼럼을 지정해서 userid 와 함께 인자로 넣어준다
	 * ex 그룹 아이디를 변경하고싶음 --> updateTarget = {"null", "abc123"}
	 * 해당 칼럼을 변경하지 않는다면 String으로 "null"을 써주는 것에 주의하자. 아, 애초에 스트링 배열이구나.
	 */
	public synchronized static boolean updateClient(String ID, String[] updateTarget) {
		try {
			
			Statement 	stmt = connectToDB.DBStmt();
			String[] 	columns = { "pwd", "groupid" };
			
	        for(int i=0; i<updateTarget.length; i++) {
	        	
	        	if(updateTarget[i].equals("null"))
	        		continue;
	        	
	        	else {
	        		
		        	String sql = "update clients set "
		        			+ columns[i]
		        			+ "=\""
		        			+ updateTarget[i]
		        			+ "\" where "
		        			+ "userid"
		        			+ "=\""
		        			+ ID
		        			+ "\"";
		        	
		        	stmt.executeUpdate(sql);
		        	
	        	}
	        	
	        }
	        
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateTodolist(String ID, String[] updateTarget) {
		try {
			
			Statement 	stmt = connectToDB.DBStmt();
			String[] 	columns = {"mand_schedule", "chal_schedule"};
			
	        for(int i=0; i<updateTarget.length; i++) {
	        	
	        	if(updateTarget[i].equals("null"))
	        		continue;
	        	
	        	else {
	        		
		        	String sql = "update todo_list set "
		        			+ columns[i]
		        			+ "=\""
		        			+ updateTarget[i]
		        			+ "\" where "
		        			+ "ID"
		        			+ "=\""
		        			+ ID
		        			+ "\"";
		        	
		        	stmt.executeUpdate(sql);
		        	
	        	}
	        	
	        }
	        
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
