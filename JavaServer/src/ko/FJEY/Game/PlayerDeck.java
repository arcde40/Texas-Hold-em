package ko.FJEY.Game;

import io.netty.channel.Channel;

public class PlayerDeck {
	
	public String name;
	public Channel channel;
	public Cards[] deck;
	
	public PlayerDeck(String name, Channel channel) {
		this.name = name;
		this.channel = channel;
		this.deck = new Cards[2];
	}
	
	public Cards getDeck(int idx) {
		if(idx>2 || idx<1) return null;
		return deck[idx-1];
	}
	
	public void setDeck(int idx, Cards card) {
		if(idx>2 || idx<1) return;
		this.deck[idx-1] = card;
	}
	
	public Channel getChannel() {
		return this.channel;
	}
	
	public String getName() {
		return this.name;
	}
	
}
