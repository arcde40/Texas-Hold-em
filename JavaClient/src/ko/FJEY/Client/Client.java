package ko.FJEY.Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	
	public static void main(String args[]) throws Exception {
		System.out.println("Connecting..");
		
		Socket sc = new Socket("127.0.0.1", 1000);
		
		ReceiveThread r_Thread = new ReceiveThread(sc);
		
		Scanner sca = new Scanner(System.in);
		String nickname = "";
		nickname = sca.nextLine();
		
		PrintWriter pw = new PrintWriter(sc.getOutputStream());
		pw.write("CLIENT_JOIN_NICK:" + nickname);
		pw.flush();
		
		r_Thread.start();
	}
	
}
