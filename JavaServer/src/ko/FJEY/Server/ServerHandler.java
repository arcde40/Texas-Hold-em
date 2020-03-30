package ko.FJEY.Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	
	@Override
	public void channelActive(ChannelHandlerContext context) throws Exception{
		// ä�� ����� �غ� �Ϸ� / ����ڰ� ��������
		System.out.println("Channel Activated");
		Channel incoming = context.channel();
		
		for(Channel channel : channelGroup) {
			channel.write("[Server] - " + incoming.remoteAddress() + " has joined the server!\n");
		}
		channelGroup.add(incoming);
		
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext context) throws Exception{
		// ä�� ��Ȱ��ȭ / ����ڰ� ��������
		System.out.println("Channel Deactivated");
		for(Channel channel : channelGroup) {
			channel.write("[Server] - " + context.channel().remoteAddress() + " left the server.\n");
		}
		channelGroup.remove(context.channel());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext context, Object msg) throws Exception{
		// ����ڷκ��� �Է��� �޾��� ��
		Channel incoming = context.channel();
		
		String s = (String) msg;
		System.out.println("Channel Read: " + s);
		
		for(Channel channel : channelGroup) {
			if(channel != incoming) {
				channel.writeAndFlush("["+incoming.remoteAddress()+"] - "+ msg + "\n");
			}
		}
		
		incoming.writeAndFlush("[ECHO] " + msg);
		if("bye".equals(s.toLowerCase())){
			context.close();
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext context) throws Exception{
		context.flush();
	}
}
