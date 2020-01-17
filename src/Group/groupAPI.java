package Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import DBController.insertIntoDB;
import DBController.searchFromDB;
import DBController.updateDB;

public class groupAPI {
	
	public static String createGroup(String groupID) {
		boolean createStatus = insertIntoDB.addTeam(groupID);
		if(!createStatus) {
			return "이미 존재하는 아이디입니다.";
		} else {
			return "그룹 생성을 성공적으로 마쳤습니다.";
		}
	}
	
	public static String joinGroup(String userID, String teamID) {
		ResultSet client_list = searchFromDB.searchObjects("clients");
		
		String[] updateTarget;
		boolean joinStatus;
		try {
			
			while(client_list.next()) {
				if(userID.equals(client_list.getString(1))) {
					if(!findTeam(teamID)) {
						throw new SQLException();
					}
					
					String teams = client_list.getString(2);
					if(!teams.equals("null")) {
						teams += teamID+";";
					}else {
						teams = teamID+";";
					}
					
					updateTarget = new String[] { teams, "null" };
					
					joinStatus = updateDB.updateClient(userID, updateTarget);
					
					if(joinStatus) return "성공적으로 참여하였습니다.";
					else return "존재하지않는 그룹입니다.";
				}
				
			}
			
			throw new SQLException();
		}catch (SQLException e) {
			return "일치하는 그룹이 없습니다.";
		}
	}
	
	private static boolean findTeam(String teamID) {
		ResultSet client_list = searchFromDB.searchObjects("team");
		try {
			while(client_list.next()) {
				if(teamID.equals(client_list.getString(1))) {
					return true;
				}
			}
			
			throw new SQLException();
		} catch (SQLException e) {
			return false;
		}
		
	}
}
