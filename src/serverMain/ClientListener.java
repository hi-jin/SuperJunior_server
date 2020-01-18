package serverMain;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import Group.groupAPI;
import Group.progressAPI;

/**
 * client와의 통신을 보조(해석하여 메소드 실행)하는 클래스
 * 
 * @author hi-jin
 */
public class ClientListener extends Thread {

	/**
	 * client와의 통신에서 쓸 데이터 구분자
	 * ex) DELIMITER = "/" 이면
	 * 		methodName/args1/args2/...
	 * 		첫 index는 위처럼 항상 함수 이름을 붙이기로 한다.
	 * 
	 * @see #addDelimiters(String...)
	 */
	public static final String	DELIMITER = "/";
	private Socket 				client;
	private PrintWriter 		out = null;
	private BufferedReader 		in = null;
	private String				userId;
	
	public ClientListener(Socket client) {
		this.client = client;
		try {
			out = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * command에 delimiter를 추가하는 메소드
	 * 
	 * @see #DELIMITER
	 * @param strs : delimiter를 추가할 Strings
	 * @return delimiter가 추가된 String
	 */
	public static String addDelimiters(String ... strs) {
		String result = strs[0];
		for(int i = 1; i < strs.length; i++) {
			result += DELIMITER + strs[i];
		}
		return result;
	}
	
	@Override
	public void run() {
//		TODO 종료 직전에 close해야 할 자원이 더 있으면 쓰기
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if(out != null) {
				out.close();
			}
			if(in != null) {
				try {
					in.close();
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(userId == null || userId.equals("")) return;
			else Main.removeClient(this);
		}));
		
		String line;
		String[] command;
		try {
			while((line = in.readLine()) != null) {
				System.out.println(this + "=>" + line);
				command = line.split(DELIMITER);
				switch(command[0]) {
					case "login":
						ResultSet rs = DBController.searchFromDB.searchObjects("clients");
						try {
							boolean isLogined = false;
							while(rs.next()) {
								if(command[1].equals(rs.getString(1))) {
									out.println(addDelimiters("login", "1", rs.getString(2)));
									out.flush();
									userId = command[1];
									isLogined = true;
									break;
								}
							}
							if(!isLogined) {
								if(DBController.insertIntoDB.addClient(command[1])) {
									out.println(addDelimiters("login", "2"));
								} else {
									out.println(addDelimiters("login", "0"));
								}
								out.flush();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						break;
					case "group":
						if(command[1].equals("create")) {
							String status = groupAPI.createGroup(command[2]);
							out.println("group/error/"+status);
							out.flush();
						}
						else if(command[1].equals("join")) {
							String status = groupAPI.joinGroup(command[2], command[3]);
							out.println("group/error/"+status);
							out.flush();
						}
						break;
					case "progress":
						if(command[1].equals("getGroup")) {
							String status = progressAPI.getGroupProgress(command[2]);
							out.println("progress/getGroup/"+status);
							out.flush();
						}
						else if(command[1].equals("getUser")) {
							String progress = progressAPI.getProgress(command[2]);
							out.println("progress/"+progress);
							out.flush();
						}
						else if(command[1].equals("updateUser")) { //TODO user이름을 받도록 할까? 받으면 injection공격에 취약할 것 같아서 ClientListener내의 변수를 사용함.
							boolean result = progressAPI.setProgress(this.userId, Integer.parseInt(command[2]), Integer.parseInt(command[3]));
							if(result) out.println("progress/updateUser/1");
							else out.println("progress/updateUser/0");
							out.flush();
						}
						break;
				}
			}
		} catch (IOException e) {
			System.out.println(this.userId+" logout!");
			if(userId == null || userId.equals("")) return;
			else Main.removeClient(this);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		ClientListener other = (ClientListener) o;
		if(userId.equals(other.userId)) return true;
		return false;
	}
	
	@Override
	public String toString() {
		if(userId == null || userId.equals("")) return client.toString();
		else return userId;
	}
	
	public void sendUpdate() {
		out.println(addDelimiters("update"));
		out.flush();
	}
	
}
