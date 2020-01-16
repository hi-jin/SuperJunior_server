package serverMain;

import java.util.Calendar;
import java.util.Vector;

public class UpdateListener extends Thread{

	private Vector<ClientListener> 	clientList;
	private Calendar 				calendar = Calendar.getInstance();
	
	String[] time = calendar.getTime().toString().split(" ");
	String pastDate = "15"; // time[2];
	
	public UpdateListener(Vector<ClientListener> clientList) {
		this.clientList = clientList;
	}
	
	@Override
	public void run() {
		while(true) {
			String[] time = calendar.getTime().toString().split(" ");
			String nowDate = time[2];
			if(!pastDate.equals(nowDate)) {
				// 날짜가 달라지면 업데이트 메시지를 보내도록 함.
				for(ClientListener cl : clientList) {
					cl.sendUpdate();
				}
			}
			
			// 현재 날짜가 과거 날짜가 됨.
			// pastDate = nowDate;
			
			try {
				// 1분동안 sleep시킴.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
