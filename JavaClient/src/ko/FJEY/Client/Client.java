package ko.FJEY.Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	
	public static void main(String args[]) throws Exception {
		System.out.println("Connecting..");
		
		Socket sc = new Socket("fjey.kro.kr", 1000);
		
		ReceiveThread r_Thread = new ReceiveThread(sc);
		SendThread s_Thread = new SendThread(sc);
		
		Scanner sca = new Scanner(System.in);
		String nickname = "";
		nickname = sca.nextLine();
		
		PrintWriter pw = new PrintWriter(sc.getOutputStream());
		pw.write("CLIENT_JOIN_NICK:" + nickname);
		pw.flush();
		
		r_Thread.start();
		s_Thread.start();
	}
	
	
	
}
