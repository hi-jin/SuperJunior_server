package Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import DBController.insertIntoDB;
import DBController.searchFromDB;
import DBController.updateDB;

public class progressAPI {
	public static String createGroup(String groupID) {
		boolean createStatus = insertIntoDB.addTeam(groupID);
		if(!createStatus) {
			return "이미 존재하는 아이디입니다.";
		} else {
			return "그룹 생성을 성공적으로 마쳤습니다.";
		}
	}
	
	public static String joinGroup(String userID, String groupID) {
		ResultSet client_list = searchFromDB.searchObjects("clients");
		
		String[] updateTarget;
		boolean joinStatus;
		try {
			
			while(client_list.next()) {
				if(userID.equals(client_list.getString(1))) {
					
					String groups = client_list.getString(2);
					if(!groups.equals("null")) {
						groups += groupID+"+";
					}else {
						groups = groupID+"+";
					}
					System.out.println(groups);
					updateTarget = new String[] { groups, "null" };
					
					joinStatus = updateDB.updateClient(userID, updateTarget);
					
					if(joinStatus) return "성공적으로 참여하였습니다.";
					else return "존재하지않는 그룹입니다.";
				}
				
			}
			
			throw new SQLException();
		}catch (SQLException e) {
			return "일치하는 유저가 없습니다.";
		}
	}
	/**
	 * DB로부터 진척도를 가져오는 메소드
	 * 
	 * @see #getProgress
	 * @param userID : 진척도를 가져올 userID
	 * @return String형의 퍼센테이지 정보
	 */
	public static String getProgress(String userID) {
		
		ResultSet client_list = searchFromDB.searchObjects("clients");
		
		try {
			
			while(client_list.next()) {
				if(userID.equals(client_list.getString(1))) {
					
					String progress = client_list.getString(3);
					
					if(progress.equals("null")) return "0%";
					
					StringTokenizer st = new StringTokenizer(progress, "/");
					
					int finish = Integer.parseInt(st.nextToken()); 	// 끝낸 일정 개수
					int all = Integer.parseInt(st.nextToken());		// 전체 일정 개수
					int percent = (int)(((double)finish/all)*100);	// 퍼센트로 나타냄
					
					return percent+"%";
				}
				
			}
			
			throw new SQLException();
		}catch (SQLException e) {
			return "일치하는 유저가 없습니다.";
		}
	}
	
	/**
	 * DB로 진척도를 수정하는 메소드
	 * 
	 * @see #setProgress
	 * @param userID, doneSchedule, allSchedule => 진척도를 업데이트할 userID, 끝낸 일정 개수, 전체 일정 개수
	 * @return Boolean형의 성패 여부.
	 */
	public static boolean setProgress(String userID, int doneSchedule, int allSchedule) {
		ResultSet client_list = searchFromDB.searchObjects("clients");
		
		String[] updateTarget;
		try {
			while(client_list.next()) {
				if(userID.equals(client_list.getString(1))) {
					
					String progress = client_list.getString(3);
					
					if(progress.equals("null")) progress = "0/0";
					
					StringTokenizer st = new StringTokenizer(progress, "/");
					
					int finish = Integer.parseInt(st.nextToken()); 	// 과거의 끝낸 일정 개수
					int all = Integer.parseInt(st.nextToken());		// 과거의 전체 일정 개수
					
					finish += doneSchedule;
					all += allSchedule;
					
					updateTarget = new String[] { "null", finish+"/"+all };
					
					return updateDB.updateClient(userID, updateTarget);
				}
				
			}
			
			throw new SQLException();
		}catch (SQLException e) {
			return false;
		}
	}
}
