package ko.FJEY.Game;

import io.netty.channel.Channel;

public class Player {

	private Channel playerChannel;
	private String name;
	private String descAddress;
	
	private boolean isPlaying;
	private PlayerDeck playerDeck;
	private int GameID;
	
	public Player(Channel channel, String name, String descAddress) {
		this.playerChannel = channel;
		this.name = name;
		this.descAddress = descAddress;
		
		this.isPlaying = false;
	}
	
	public Channel getChannel() {
		return this.playerChannel;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescAddress() {
		return descAddress;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public void toggleIsPlaying() {
		isPlaying = !isPlaying;
	}
	
	public PlayerDeck getPlayerDeck() {
		return this.playerDeck;
	}
	
	public void registerGamePlayer() throws Exception{
		if(this.isPlaying) {
			throw new Exception("Player is already playing a game.");
		}

		toggleIsPlaying();
		registerPlayerDeck(new PlayerDeck(getName(), getChannel()));
	}
	
	public void registerPlayerDeck(PlayerDeck playerDeck) {
		this.playerDeck = playerDeck;
	}
}
