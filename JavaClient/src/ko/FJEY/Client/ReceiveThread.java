package ko.FJEY.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread{
	
	public Socket sc;
	
	public ReceiveThread(Socket socket){
		this.sc = socket;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			
			String s;
			while(true) {
				s = br.readLine();
				if(s == null) {
					System.out.println("서버와의 연결이 끊어졌습니다.");
					break;
				}else {
					System.out.println(s);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}