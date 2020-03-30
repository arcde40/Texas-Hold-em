package ko.FJEY.Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class ServerInitializer extends ChannelInitializer<SocketChannel>{


	
	
	
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipline = socketChannel.pipeline();
		
		//pipline.addLast(sslContext.newHandler(socketChannel.alloc()));
//		pipline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipline.addLast(new StringDecoder());
		pipline.addLast(new StringEncoder());
		pipline.addLast(new ServerHandler());
		
	}
	
}
