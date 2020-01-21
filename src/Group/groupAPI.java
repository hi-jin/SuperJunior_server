package Group;

import java.sql.ResultSet;
import java.sql.SQLException;

import DBController.deleteFromDB;
import DBController.insertIntoDB;
import DBController.searchFromDB;
import DBController.updateDB;

public class groupAPI {
	
	public static String createGroup(String userID, String groupID) {
		boolean createStatus = insertIntoDB.addTeam(groupID);
		joinGroup(userID, groupID);
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
					if(!teams.equals("null;")) {
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
	
	public static String quitGroup(String userID, String teamID) {
		ResultSet client_list = searchFromDB.searchObjects("clients");
		String[] updateTarget;
		StringBuilder teams = new StringBuilder();
		boolean status = false;
		try {
			
			while(client_list.next()) {
				if(userID.equals(client_list.getString(1))) {
					String[] userTeams = client_list.getString(2).split(";");
					for(String team : userTeams) {
						if(!team.equals(teamID)) {
							teams.append(team+";");
						}
					}
					
					String show = teams.toString();
					if(show.equals("")) {
						show = "null;";
					}
					
					updateTarget = new String[] { show, "null" };
					status = updateDB.updateClient(userID, updateTarget);
				}
			}
			
			client_list = searchFromDB.searchObjects("clients");
			int count = 0;
			while(client_list.next()) {
				String[] userTeams = client_list.getString(2).split(";");
				for(String team : userTeams) {
					if(team.equals(teamID)) {
						count++;
					}
				}
			}
			
			if(count == 0) {
				System.out.println(deleteFromDB.deleteTeam(teamID));
			}
			if(status) {
				return "성공적으로 탈퇴하셨습니다.";
			}else {
				return "DB 오류!";
			}
		} catch (SQLException e) {
			return "넌 존재하지 않는 유저입니다;;;";
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
