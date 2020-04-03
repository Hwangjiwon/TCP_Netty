import io.netty.channel.ChannelHandler.Sharable;

import java.io.UnsupportedEncodingException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	byte[] bytes = null;
	String message = null;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		
		bytes = new byte[100];
		
		int readByteCount = msg.toString().length();
		try {
			message = new String(bytes, 0, readByteCount, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[Server : 데이터 받기 성공]: " + msg);
	
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
