package ko.FJEY.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendThread extends Thread{
	
	public Socket sc;
	
	public SendThread(Socket socket){
		this.sc = socket;
	}
	
	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(System.in);
			PrintWriter pw = new PrintWriter(sc.getOutputStream());
			while(true) {
				String s = scanner.next();
				pw.write(s);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}