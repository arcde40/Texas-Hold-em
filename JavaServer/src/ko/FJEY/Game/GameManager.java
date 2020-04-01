package ko.FJEY.Game;

import java.util.ArrayList;

public class GameManager {
	
	private Player[] player;
	private Deck deck;
	private int GameID;
	private Cards[] table;
	
	public GameManager(Player p1, Player p2, int GameID) throws Exception {
		player = new Player[2];
		this.player[0] = p1;
		this.player[1] = p2;
		this.GameID = GameID;
		this.deck = new Deck(GameID);
		this.table = new Cards[5];
		
		this.player[0].registerGamePlayer();
		this.player[1].registerGamePlayer();
		
		deck.shuffle(100);
	}
	
	public void notifyPlayer(Player p, String s) {
		System.out.println("Notify - "+ s+"\n");
		if(p.getChannel().isActive()) p.getChannel().writeAndFlush(s+"\n");
		else return;
	}
	
	public void startGame() {
		// 카드 두장 나눠주기
		player[0].getPlayerDeck().setDeck(1, Cards.getCardByName(deck.next()));
		player[0].getPlayerDeck().setDeck(2, Cards.getCardByName(deck.next()));
		notifyPlayer(player[0], "GAMEINFO_GIVE_TWO:" + player[0].getPlayerDeck().getDeck(1).getName() + "," + player[0].getPlayerDeck().getDeck(2).getName());
		player[1].getPlayerDeck().setDeck(1, Cards.getCardByName(deck.next()));
		player[1].getPlayerDeck().setDeck(2, Cards.getCardByName(deck.next()));
		//notifyPlayer(player[1], "GAMEINFO_GIVE_TWO:" + player[1].getPlayerDeck().getDeck(1).getName() + "," + player[1].getPlayerDeck().getDeck(2).getName());

		bet();
		
		for(int i = 0; i < 3; i++) {
			table[i] = Cards.getCardByName(deck.next());
			notifyPlayer(player[0], "GAMEINFO_GIVE_ONE:"+table[i].getName());
			//notifyPlayer(player[1], "GAMEINFO_GIVE_ONE:"+table[i].getName());
		}
		
		
		
		bet();
		
		for(int i = 0; i < 2; i++) {
			table[3+i] = Cards.getCardByName(deck.next());
			notifyPlayer(player[0], "GAMEINFO_GIVE_ONE:"+table[3+i].getName());
			//notifyPlayer(player[1], "GAMEINFO_GIVE_ONE:"+table[3+i].getName());
			bet();
		}
		
		getCombination(player[0], table);
		//getCombination(player[1], table);

	}
	
	public void bet() {
		notifyPlayer(player[0], "GAMEINFO_BATTING");
		//notifyPlayer(player[1], "GAMEINFO_BATTING");
	}
	
	public void getCombination(Player pDeck, Cards[] table) {
		Cards[] cards = new Cards[7];
		for(int i = 0; i < 2; i++) cards[i] = pDeck.getPlayerDeck().getDeck(i+1);
		for(int i = 0; i < 5; i++) cards[i+2] = table[i];
		
		// Combination Top-
		
		// 로열 스트레이트 플러쉬
		for(Cards type : new Cards[] {Cards.DIAMOND, Cards.CLOVER, Cards.SPADE, Cards.HEART}) {
			if(hasCard(cards, type, 13) && hasCard(cards, type, 12) && hasCard(cards, type, 11) && hasCard(cards, type, 10) && hasCard(cards, type, 14)) {
				// 와! 대박! 로열 스트레이트 플러쉬
			}
		}
		
		
		Cards[] combination = {};
		
		// 스트레이트 플러쉬
		if((combination = hasFlush(hasStraight(cards)))[0] != Cards.NO) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "STRAIGHT_FLUSH");
		}
		
		// 4 카드
		else if((combination = hasMultiple(cards, 4))[0] != Cards.NO) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "FOUR_CARD");

		}
		
		// 풀하우스
		else if((combination = hasFullHouse(cards))[0] != Cards.NO) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "FULL_HOUSE");

		}
		
		// 플러쉬
		else if((combination = hasFlush(cards))[0] != Cards.NO) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "FLUSH");

		}
		
		// 스트레이트
		else if((combination = hasStraight(cards))[0] != Cards.NO) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "STRAIGHT");

		}
		
		// 트리플
		else if((combination = hasMultiple(cards, 3))[0] != Cards.NO) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "TRIPLE");

		}
		
		// 투 페어
		else if((combination = hasMultiple(cards,2))[0] != Cards.NO && combination.length == 4) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "TWO_PAIR");

		}
		
		// 원 페어
		else if((combination = hasMultiple(cards,2))[0] != Cards.NO) {
			// TODO
			notifyPlayer(pDeck, combination[0].getNumber() + "ONE_PAIR");

		}
		
		// 하이 카드
		else {
			// TODO
			notifyPlayer(pDeck, getTop(cards).getNumber() + "HIGH_CARD");

		}
		
		
	}
	
	public boolean hasCard(Cards[] cards, Cards card) {
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].equals(card)) return true;
		}
		return false;
	}
	
	public boolean hasCard(Cards[] cards, Cards type, int num) {
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].getType() == type.getType() && cards[i].getNumber() == num) return true;
		}
		return false;
	}
	
	public boolean hasCard(Cards[] cards, int num) {
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].getNumber() == num) return true;
		}
		return false;
	}
	
	public Cards[] hasStraight(Cards[] cards) {
		Cards[] number = {Cards._2, Cards._3, Cards._4, Cards._5, Cards._6, Cards._7, Cards._8, Cards._9, Cards._10, Cards.J, Cards.Q, Cards.K, Cards.A};
		ArrayList<Cards> queue = new ArrayList<Cards>();
		int currentNumber = number.length - 1;
		for(int k = currentNumber; k >= 0; k--) {
			if(hasCard(cards, number[k].getNumber())) {
				for(int j = 1; j <= 4; j++) {
					if(k-j > 0 && hasCard(cards, number[k-j].getNumber())) {
						for(Cards c : cards) {
							if(c.isSameNumber(number[k-j]) != Cards.NO) {
								queue.add(c);
							}
						}
						if(j == 4) {
							return queue.toArray(new Cards[queue.size()]);
						}
					}else break;
				}
			}
		}
		
		return new Cards[] {Cards.NO};
	}
	
	public Cards[] hasFlush(Cards[] cards) {
		Cards[] patterns = {Cards.CLOVER, Cards.DIAMOND, Cards.HEART, Cards.SPADE};
		ArrayList<Cards> queue = new ArrayList<Cards>();
		for(Cards pattern : patterns) {
			int streak = 0;
			for(Cards c : cards) {
				if(c.isSameType(pattern) != Cards.NO) {
					queue.add(c);
					streak++;
				}
			}
			if(streak >= 5) return queue.toArray(new Cards[queue.size()]);
			queue.clear();
		}
		return new Cards[] {Cards.NO};
	}
	
	public Cards[] hasMultiple(Cards[] cards, int time) {
		Cards[] numbers = {Cards._2, Cards._3, Cards._4, Cards._5, Cards._6, Cards._7, Cards._8, Cards._9, Cards._10, Cards.J, Cards.Q, Cards.K, Cards.A};
		ArrayList<Cards> f_Queue = new ArrayList<Cards>();
		ArrayList<Cards> queue = new ArrayList<Cards>();
		for(int p = numbers.length - 1; p >= 0; p--) {
			int streak = 0;
			for(Cards c : cards) {
				if(c.getNumber() == numbers[p].getNumber()) {
					queue.add(c);
					streak++;
				}
			}
			if(streak == time) f_Queue.addAll(queue);
			queue.clear();
		}
		if(f_Queue.isEmpty()) return new Cards[] {Cards.NO};
		return f_Queue.toArray(new Cards[f_Queue.size()]);
	}
	
	public Cards[] hasFullHouse(Cards[] cards){
		ArrayList<Cards> queue = new ArrayList<Cards>();
		Cards[] triple = hasMultiple(cards, 3);
		if(triple[0] == Cards.NO) return triple;
		Cards[] temp = new Cards[cards.length-3];
		int cursor = 0;
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].getNumber() != triple[0].getNumber()) {
				temp[cursor++] = cards[i]; 
			}
		}
		Cards[] twoPair = hasMultiple(temp, 2);
		if(twoPair[0] == Cards.NO) return twoPair;
		return triple;
	}
	
	public Cards getTop(Cards[] cards) {
		Cards max = cards[0];
		for(Cards c : cards) {
			if(c.getNumber() > max.getNumber()) {
				max = c;
			}
		}
		
		return max;
	}
}
