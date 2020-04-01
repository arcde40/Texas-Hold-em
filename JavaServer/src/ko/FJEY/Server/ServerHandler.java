package ko.FJEY.Server;

import java.util.HashMap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import ko.FJEY.Game.GameManager;
import ko.FJEY.Game.Player;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	private static Channel waitingChannel;
	private static HashMap<Channel, String> nickNameMap = new HashMap<Channel, String>();
	
	
	@Override
	public void channelActive(ChannelHandlerContext context) throws Exception{
		// 채널 입출력 준비 완료 / 사용자가 들어왔을때
		System.out.println("Channel Activated");
		Channel incoming = context.channel();
		
		for(Channel channel : channelGroup) {
			channel.write("[Server] - " + incoming.remoteAddress() + " has joined the server!\n");
		}
		channelGroup.add(incoming);
		
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext context) throws Exception{
		// 채널 비활성화 / 사용자가 나갔을때
		System.out.println("Channel Deactivated");
		for(Channel channel : channelGroup) {
			channel.write("[Server] - " + context.channel().remoteAddress() + " left the server.\n");
		}
		channelGroup.remove(context.channel());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext context, Object msg) throws Exception{
		// 사용자로부터 입력을 받았을 때
		Channel incoming = context.channel();
		
		String s = (String) msg;
		System.out.println("Channel Read: " + s);
		
		if(s.startsWith("CLIENT_JOIN_NICK:")) {
			String nick = s.substring(17);
			incoming.writeAndFlush("Oh, Hi! " + nick);
			System.out.println("Sent Message");
			nickNameMap.put(incoming, nick);
			/*if(channelGroup.size() % 2 == 1) {
				incoming.writeAndFlush("CLIENT_NOT_ENOUGH\n");
				waitingChannel = incoming;
				
			}else */{
				Channel ch1 = incoming;
				Channel ch2 = incoming;
				
				ch1.writeAndFlush("CLIENT_MATCHED_START\n");
				ch2.writeAndFlush("CLIENT_MATCHED_START\n");
				GameManager gm = new GameManager(new Player(ch1, nickNameMap.get(ch1), ch1.remoteAddress().toString()), new Player(ch2, nickNameMap.get(ch2), ch2.remoteAddress().toString()), 0);
				gm.startGame();
			}
			/*for(Channel channel : channelGroup) {
				//if(channel != incoming) {
					if(!nickNameMap.containsKey(incoming)) {
						channel.writeAndFlush("["+incoming.remoteAddress()+"] - "+ msg + "\n");
					}else {
						channel.writeAndFlush("["+nickNameMap.get(incoming)+"] - "+ msg + "\n");
					}
				//}
			}*/
			
			
		}
		//incoming.writeAndFlush("[ECHO] " + msg);
		if("bye".equals(s.toLowerCase())){
			context.close();
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext context) throws Exception{
		context.flush();
	}
}
