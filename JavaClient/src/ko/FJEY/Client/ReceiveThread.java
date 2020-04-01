package ko.FJEY.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

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
			ArrayList<String> personalCardList = new ArrayList<String>();
			ArrayList<String> shareCardList = new ArrayList<String>();
			
			while(true) {
				s = br.readLine();
				switch (s.split(":")[0]) {
				case "GAMEINFO_GIVE_TWO":
					System.out.println("두장의 카드를 받았습니다.");
					
					String[] Arr = s.split(":");
					String[] str1 = Arr[1].split(",");
					
					switch (str1[0].charAt(0)) {
					case 'c':
						personalCardList.add("♣"+ str1[0].substring(1));
						break;
					case 'h':
						personalCardList.add("♥"+ str1[0].substring(1));
						break;
					case 's':
						personalCardList.add("♠"+ str1[0].substring(1));
						break;
					case 'd':
						personalCardList.add("◆"+ str1[0].substring(1));
						break;
					}
					
					switch (str1[1].charAt(0)) {
					case 'c':
						personalCardList.add("♣"+ str1[1].substring(1));
						break;
					case 'h':
						personalCardList.add("♥"+ str1[1].substring(1));
						break;
					case 's':
						personalCardList.add("♠"+ str1[1].substring(1));
						break;
					case 'd':
						personalCardList.add("◆"+ str1[1].substring(1));
						break;
					}
					System.out.println("P["+personalCardList.get(0)+","+personalCardList.get(1)+"]");
					//앞에 P는 개인(Personal)을 뜻함 (개인 카드라고 쉽게 파악하기 위해서 씀)
					
					break;
					
				case "GAMEINFO_GIVE_ONE":
					System.out.println("한장의 공유카드가 주어졌습니다.");
					
					Arr = s.split(":");
					str1 = Arr[1].split(",");
					
					switch (str1[0].charAt(0)) {
					case 'c':
						shareCardList.add("♣"+ str1[0].substring(1));
						break;
					case 'h':
						shareCardList.add("♥"+ str1[0].substring(1));
						break;
					case 's':
						shareCardList.add("♠"+ str1[0].substring(1));
						break;
					case 'd':
						shareCardList.add("◆"+ str1[0].substring(1));
						break;
					}
					System.out.println("S"+shareCardList);
					// 앞에 S는 공유(share)을 뜻함 (공유 카드라고 쉽게 파악하기 위해서 씀)
					break;
					
				case "GAMEINFO_BATTING":
					System.out.println("배팅할 차례입니다.");
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
				
				
				/* if(s == null) { System.out.println("서버와의 연결이 끊어졌습니다."); break; }else { }
				System.out.println(s); */
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}