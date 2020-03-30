package ko.FJEY.Game;

import java.util.Scanner;

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
		
		//getCombination();
	}
	
	public void bet() {
		notifyPlayer(player[0], "GAMEINFO_BATTING");
		//notifyPlayer(player[1], "GAMEINFO_BATTING");
	}
	
	public void getCombination(PlayerDeck pDeck, Cards[] table) {
		Cards[] cards = new Cards[7];
		for(int i = 0; i < 2; i++) cards[i] = pDeck.getDeck(i);
		for(int i = 0; i < 5; i++) cards[i+2] = table[i];
		
		// 로열 스트레이트 플러쉬
		for(Cards type : new Cards[] {Cards.DIAMOND, Cards.CLOVER, Cards.SPADE, Cards.HEART}) {
			if(hasCard(cards, type, "K") && hasCard(cards, type, "Q") && hasCard(cards, type, "J") && hasCard(cards, type, "10") && hasCard(cards, type, "A")) {
				// 와! 대박! 로열 스트레이트 플러쉬
			}
		}
		
		
		// 스트레이트 플러쉬
		
	}
	
	public boolean hasCard(Cards[] cards, Cards card) {
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].equals(card)) return true;
		}
		return false;
	}
	
	public boolean hasCard(Cards[] cards, Cards type, String num) {
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].getType() == type.getType() && cards[i].getNumber() == num) return true;
		}
		return false;
	}
	
	public boolean hasCard(Cards[] cards, String num) {
		for(int i = 0; i < cards.length; i++) {
			if(cards[i].getNumber() == num) return true;
		}
		return false;
	}
	
	public Cards[] hasStraight(Cards[] cards) {
		Cards[] number = {Cards._2, Cards._3, Cards._4, Cards._5, Cards._6, Cards._7, Cards._8, Cards._9, Cards._10, Cards.J, Cards.Q, Cards.K, Cards.A};
		int streak;
		int currentNumber = number.length - 1;
		for(int k = currentNumber; k >= 0; k--) {
			for(int i = 0; i < cards.length; i++) {
				if(hasCard(cards, number[k].getNumber())) {
					for(int j = 0; j < 4; j++) {
						if(k-j > 0 && hasCard(cards, number[k-j].getNumber())) {
							if(j == 3) {
								
							}
						}
					}
				}
			}
		}
		
		return null;
	}
	
}
