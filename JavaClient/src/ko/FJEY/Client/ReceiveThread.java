package ko.FJEY.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

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
					
					String[] Arr = s.split(":");
					String[] str = Arr[1].split(",");
					
					System.out.println("두장의 카드를 받았습니다.");
					
					switch (str[0].charAt(0)) {
					case 'c':
						personalCardList.add("♣"+ str[0].substring(1));
						break;
					case 'h':
						personalCardList.add("♥"+ str[0].substring(1));
						break;
					case 's':
						personalCardList.add("♠"+ str[0].substring(1));
						break;
					case 'd':
						personalCardList.add("◆"+ str[0].substring(1));
						break;
					}
					
					switch (str[1].charAt(0)) {
					case 'c':
						personalCardList.add("♣"+ str[1].substring(1));
						break;
					case 'h':
						personalCardList.add("♥"+ str[1].substring(1));
						break;
					case 's':
						personalCardList.add("♠"+ str[1].substring(1));
						break;
					case 'd':
						personalCardList.add("◆"+ str[1].substring(1));
						break;
					}
					System.out.println("P["+personalCardList.get(0)+","+personalCardList.get(1)+"]");
					//앞에 P는 개인(Personal)을 뜻함 (개인 카드라고 쉽게 파악하기 위해서 씀)
					break;
					
				case "GAMEINFO_GIVE_ONE":
					
					Arr = s.split(":");
					str = Arr[1].split(",");
					
					System.out.println("한장의 공유카드가 주어졌습니다.");
					
					switch (str[0].charAt(0)) {
					case 'c':
						shareCardList.add("♣"+ str[0].substring(1));
						break;
					case 'h':
						shareCardList.add("♥"+ str[0].substring(1));
						break;
					case 's':
						shareCardList.add("♠"+ str[0].substring(1));
						break;
					case 'd':
						shareCardList.add("◆"+ str[0].substring(1));
						break;
					}
					System.out.println("S"+shareCardList);
					// 앞에 S는 공유(share)을 뜻함 (공유 카드라고 쉽게 파악하기 위해서 씀)
					break;
					
					// GAMEINFO_BET:콜 가능 여부(Y/N),체크 가능 여부(Y/N),리레이즈 가능 여부(Y/N),최소값,현재 판돈
				case "GAMEINFO_BET":
					
					Arr = s.split(":");
					str = Arr[1].split(",");
					
					System.out.println("배팅할 차례입니다. 현재 판돈은 "+str[4]+"개 입니다.");
					System.out.println("벳은 1, 콜은 2, 체크는 3, 레이즈는 4, 리레이즈는 5, 폴드는 6를 입력하세요.");
				
					Scanner scan = new Scanner(System.in);
					PrintWriter pw = new PrintWriter(sc.getOutputStream());
					
					
					
					int bettingWay = scan.nextInt();
					
					switch (bettingWay) {
					case 1:
						System.out.println("1개 벳은 1, 10개 벳은 2, 100개 벳은 3을 입력하세요.");
						int betWay = scan.nextInt();
						
						switch (betWay) {
						case 1:
							pw.write("BET_BET:1");
			            	System.out.println("1개 벳.");
			            	break;
						case 2:
							pw.write("BET_BET:10");
			            	System.out.println("10개 벳.");
			            	break;
						case 3:
							pw.write("BET_BET:100");
			            	System.out.println("100개 벳.");
			            	break;
						}
						break;
					
					case 2:
						
						switch (str[0].charAt(0)) {
			            case 'Y':
			            	pw.write("BET_CALL:"+str[3]);
			            	System.out.println(str[3]+"개 콜.");
			            	break;
			            case 'N':
			            	System.out.println("콜을 할 수 없습니다.");
			            	break;
						}
						break;
						
					case 3:
						
						switch (str[1].charAt(0)) {
						case 'Y':
			            	pw.write("BET_CHECK");
			            	System.out.println("체크.");
			            	break;
			            case 'N':
			            	System.out.println("체크를 할 수 없습니다.");
			            	break;
						}
						break;
						
					case 4:
						System.out.println("x2는 1, x4는 2, 사용자 지정은 3을 입력하세요.");
						int raiseWay = scan.nextInt();
						
						switch (raiseWay) {
						case 1:
							pw.write("BET_RAISE:"+(Integer.parseInt(str[3])*2));
							System.out.println((Integer.parseInt(str[3])*2)+"개 레이즈.");
							break;
						case 2:
							pw.write("BET_RAISE:"+(Integer.parseInt(str[3])*4));
							System.out.println((Integer.parseInt(str[3])*4)+"개 레이즈.");
							break;
						case 3:
							System.out.println("레이즈 할 숫자를 입력하세요.");
							int raiseNum = scan.nextInt();
							pw.write("BET_RAISE:"+raiseNum);
							System.out.println(raiseNum+"개 레이즈.");
							break;
						}
						break;
						
					case 5:
						
						switch (str[2].charAt(0)) {
						case 'Y':
							System.out.println("x2는 1, x4는 2, 사용자 지정은 3을 입력하세요.");
							raiseWay = scan.nextInt();
							
							switch (raiseWay) {
							case 1:
								pw.write("BET_RAISE:"+(Integer.parseInt(str[3])*2));
								System.out.println((Integer.parseInt(str[3])*2)+"개 리레이즈.");
								break;
							case 2:
								pw.write("BET_RAISE:"+(Integer.parseInt(str[3])*4));
								System.out.println((Integer.parseInt(str[3])*4)+"개 리레이즈.");
								break;
							case 3:
								System.out.println("리레이즈 할 숫자를 입력하세요.");
								int raiseNum = scan.nextInt();
								pw.write("BET_RAISE:"+raiseNum);
								System.out.println(raiseNum+"개 리레이즈.");
								break;
							}
							break;
							
						case 'N':
							System.out.println("리레이즈를 할 수 없습니다.");
							break;
						}
						break;
						
					case 6:
						System.out.println("폴드.");
						break;
					}
					break;
					
				case "GAMEINFO_REMAINING_CHIP":
					Arr = s.split(":");
					System.out.println("현재 당신에게 남은 칩은 "+Arr[1]+"개 입니다.");
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