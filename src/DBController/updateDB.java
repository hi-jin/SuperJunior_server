package DBController;

import java.sql.SQLException;
import java.sql.Statement;

import data.DBInfo;

public class updateDB {
	/*
	 * updateTarget 배열에 업데이트할 칼럼을 지정해서 userid 와 함께 인자로 넣어준다
	 * ex ) 그룹 아이디를 변경하고싶다. --> updateTarget = { "group2", "null" }
	 * 해당 칼럼을 변경하지 않는다면 String으로 "null"을 써주는 것에 주의하자.
	 */
	public synchronized static boolean updateClient(String ID, String[] updateTarget) {
		try {
			
			String[] 	columns = {  "teams", "progress" };
			
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
		        	
		        	DBInfo.Dbstmt.executeUpdate(sql);
		        	
	        	}
	        	
	        }
	        
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// 혹시 그룹 아이디를 변경할 일이 있다면 사용.
	public static boolean updateTeam(String ID, String newTeamID) {
		try {
			
		    String sql = "update team set "
		        	+ "teamid"
		        	+ "=\""
		        	+ newTeamID
		        	+ "\" where "
		        	+ "teamid"
		        	+ "=\""
		        	+ ID
		        	+ "\"";
		        	
		    DBInfo.Dbstmt.executeUpdate(sql);
		    
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
