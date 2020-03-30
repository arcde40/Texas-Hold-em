package ko.FJEY.Client;

import java.net.Socket;


public class Client {
	
	public static void main(String args[]) throws Exception {
		System.out.println("Connecting..");
		
		Socket sc = new Socket("localhost", 1000);
		
		ReceiveThread r_Thread = new ReceiveThread(sc);
		SendThread s_Thread = new SendThread(sc);
		r_Thread.start();
		s_Thread.start();
		
	}
	
	
	
	
	
}
