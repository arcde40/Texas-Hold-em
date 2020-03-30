package ko.FJEY.Game;

import java.util.Random;

public class Deck {
	
	
	int currentCursor;
	int GameID;
	
	String[] deck = {"h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "h10", "hJ", "hQ", "hK",
			"d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10", "dJ", "dQ", "dK",
			"c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "cJ", "cQ", "cK",
			"s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "sJ", "sQ", "sK"};
	
	public Deck(int GameID) {
		this.currentCursor = deck.length;
		this.GameID = GameID;
	}
	
	public void shuffle(int time) {
		Random r = new Random();
		for(int i = 0; i < time; i++ ) {
			int t1 = r.nextInt(52);
			int t2 = r.nextInt(52);
			String temp = deck[t1];
			deck[t1] = deck[t2];
			deck[t2] = temp;
		}
	}
	
	public String next() {
		if(currentCursor == 0) return null;
		return deck[--currentCursor];
	}
	
	public int getCurrentCursor() {
		return currentCursor;
	}
	
}
