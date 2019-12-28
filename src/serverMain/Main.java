package serverMain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Main {

	private static final int PORT = 50000;
	
//	TODO socket을 저장할지, ClientListener 스레드 자체를 저장할지 고민할 필요가 있음 (전자는 접속 시, 후자는 로그인 시 저장)
	private static Vector<ClientListener> clientList;		// 접속한 클라이언트 저장
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(PORT);
			
			Socket client = null;
			
			// TODO 종료 직전 반드시 실행해야 할 메소드들 적기
			// 종료 직전에 실행되는 스레드
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				if(!server.isClosed()) {
					try {
						server.close();
						System.out.println("서버가 성공적으로 종료되었습니다.");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}));
			
			while(true) {
				client = server.accept();
				System.out.println(client.getInetAddress()+" 클라이언트 접속"); // connection test
				ClientListener clientThread = new ClientListener(client);
//				clientThread.setDaemon(true);		// TODO Daemon으로 만들까말까 만들까말까 던던던던 던져 던져
				clientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static void addClient(ClientListener client) {		// TODO synchronized 키워드를 필수적으로 써야하는지는 알아봐야 할 것 같음
		clientList.add(client);
	}
	
	public synchronized static void removeClient(ClientListener client) {
		clientList.remove(client);
	}
}
