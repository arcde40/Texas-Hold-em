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
				switch (s.split(":")[0]) {
				case "GAMEINFO_GIVE_TWO":
					System.out.println("두장의 카드를 받았습니다.");
					
					String[] Arr = s.split(":");
					String[] str1 = Arr[1].split(",");
					
					switch (str1[0].charAt(0)) {
					case 'c':
						System.out.print("♣");
						break;
					case 'h':
						System.out.print("♥");
						break;
					case 's':
						System.out.print("♠");
						break;
					case 'd':
						System.out.print("◆");
						break;
					}
					System.out.print(str1[0].substring(1)+",");
					
					switch (str1[1].charAt(0)) {
					case 'c':
						System.out.print("♣");
						break;
					case 'h':
						System.out.print("♥");
						break;
					case 's':
						System.out.print("♠");
						break;
					case 'd':
						System.out.print("◆");
						break;
					}
					System.out.println(str1[1].substring(1));
					
					break;
				case "GAMEINFO_GIVE_ONE":
					System.out.println("한장의 공유카드가 주어졌습니다.");
					
					Arr = s.split(":");
					str1 = Arr[1].split(",");
					switch (str1[0].charAt(0)) {
					case 'c':
						System.out.print("♣");
						break;
					case 'h':
						System.out.print("♥");
						break;
					case 's':
						System.out.print("♠");
						break;
					case 'd':
						System.out.print("◆");
						break;
					}
					System.out.println(str1[0].substring(1));
					break;
				case "GAMEINFO_OPEN_CARD":
					System.out.println("패를 공개합니다.");
					break;	
				case "GAMEINFO_WHO_WIN":
					System.out.println("~~~ 님이 이겼습니다.");
					break;	
				case "GAMEINFO_GAME_SET":
					System.out.println("게임이 끝났습니다.");
					break;
				case "GAMEINFO_GAME_START":
					System.out.println("게임을 시작합니다.");
					break;
				}
				
				
				/* if(s == null) { System.out.println("서버와의 연결이 끊어졌습니다."); break; }else { }*/
				//System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}